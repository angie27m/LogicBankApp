FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
RUN mkdir -p /img
ADD ./target/spring-boot-docker.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
