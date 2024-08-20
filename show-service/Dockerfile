FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY /build/libs/show-service-0.0.1-SNAPSHOT.jar /app/show-service.jar

ENTRYPOINT ["java", "-jar", "/app/show-service.jar"]