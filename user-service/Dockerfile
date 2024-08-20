FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY /build/libs/user-service-0.0.1-SNAPSHOT.jar /app/user-service.jar

ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]