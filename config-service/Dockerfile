FROM amazoncorretto:21-alpine-jdk
COPY ../keystore/apiEncryptionKey.jks apiEncryptionKey.jks
COPY build/libs/config-service-1.0.0.jar config-service.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "-Dspring.rabbitmq.host=rabbitmq", "config-service.jar"]