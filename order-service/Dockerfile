FROM amazoncorretto:21-alpine-jdk
COPY build/libs/order-service-1.0.0.jar order-service.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "order-service.jar"]
