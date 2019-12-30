
package org.pixer.pixer.rest;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

/**
 *
 * @author ador
 */
@ApplicationScoped
@Path("/pixer")
public class PixerEndpoint {

	private Sse sse;
	private static final Logger LOG = Logger.getLogger(PixerEndpoint.class.getName());
	
	private SseBroadcaster broadcaster;
	
	private Set<FillMsg> cache = new HashSet<>();

	@Context
	public void setSse(Sse sse) {
		this.sse = sse;
		broadcaster = sse.newBroadcaster();
		LOG.info("Broadcaster created!");
	}
	
	@GET
	@Path("/index.html")
	public Response indexHTML() {
		InputStream inputStream = this.getClass().getResourceAsStream("/webapp/index.html");
		
		return Response.ok(inputStream, MediaType.TEXT_HTML).build();
	} 
	
	@GET
	@Path("/config")
	@Produces(MediaType.APPLICATION_JSON)
	public PixerConfig getConfig() {
		return new PixerConfig(10, 10);
	}
	
	@GET
	@Path("/connect")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public void connect(@Context SseEventSink sseEventSink) {
		OutboundSseEvent.Builder eventBuilder = sse.newEventBuilder();
		
		for (FillMsg msg : cache) {
			OutboundSseEvent fillEvent = eventBuilder
				.name("fill")
				.mediaType(MediaType.APPLICATION_JSON_TYPE)
				.data(FillMsg.class, msg)
				.reconnectDelay(4000)
				.build();
			
			sseEventSink.send(fillEvent);
		}
		
		broadcaster.register(sseEventSink);
	}
	
	@POST
	@Path("/fill")
	@Consumes(MediaType.APPLICATION_JSON)
	public void fill(FillMsg msg) {
		OutboundSseEvent.Builder eventBuilder = sse.newEventBuilder();
		
		OutboundSseEvent fillEvent = eventBuilder
			.mediaType(MediaType.APPLICATION_JSON_TYPE)
			.data(FillMsg.class, msg)
			.reconnectDelay(4000)
			.build();
		
		cache.add(msg);
		
		broadcaster.broadcast(fillEvent);
		
	}
}
