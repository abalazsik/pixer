FROM openjdk:11.0.5-stretch

RUN ["mkdir", "/home/pixer"]
COPY target/pixer-thorntail.jar /home/pixer
COPY target/pixer.war /home/pixer
WORKDIR /home/pixer
CMD ["java", "-Djava.net.preferIPv4Stack=true", "-jar", "pixer-thorntail.jar", "pixer.war"]
