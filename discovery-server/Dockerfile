FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY /build/libs/discovery-server-0.0.1-SNAPSHOT.jar /app/discovery-server.jar

ENTRYPOINT ["java", "-jar", "/app/discovery-server.jar"]