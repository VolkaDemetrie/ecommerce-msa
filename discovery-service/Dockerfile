FROM amazoncorretto:21-alpine-jdk
COPY build/libs/discovery-service-0.0.1.jar discovery-service.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "discovery-service.jar"]