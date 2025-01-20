FROM amazoncorretto:21-alpine-jdk
COPY build/libs/user-service-1.0.0.jar user-service.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "user-service.jar"]