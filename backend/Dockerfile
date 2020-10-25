FROM openjdk:13-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=backend-1.0.2-SNAPSHOT.jar
COPY ./target/${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]