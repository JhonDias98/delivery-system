FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY pom.xml ./pom.xml
COPY orders-service/pom.xml orders-service/pom.xml

RUN mvn -q -B -DskipTests -f orders-service/pom.xml dependency:go-offline

COPY orders-service/src orders-service/src

RUN mvn -q -B -DskipTests -f orders-service/pom.xml clean package spring-boot:repackage

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=build /build/orders-service/target/orders-service-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]