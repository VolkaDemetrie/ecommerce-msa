FROM amazoncorretto:21-alpine-jdk
COPY build/libs/api-gateway-service-1.0.0.jar api-gateway-service-1.0.0.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "api-gateway-service-1.0.0.jar"]