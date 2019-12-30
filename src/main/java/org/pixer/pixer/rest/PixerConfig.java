package org.pixer.pixer.rest;

/**
 *
 * @author ador
 */
public class PixerConfig {

	private int width;
	private int height;

	public PixerConfig() {
	}

	public PixerConfig(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
