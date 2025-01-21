FROM amazoncorretto:21-alpine-jdk
COPY build/libs/catalog-service-1.0.0.jar catalog-service.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "catalog-service.jar"]