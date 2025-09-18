FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY pom.xml ./pom.xml
COPY payments-service/pom.xml payments-service/pom.xml

RUN mvn -q -B -DskipTests -f payments-service/pom.xml dependency:go-offline

COPY payments-service/src payments-service/src
RUN mvn -q -B -DskipTests -f payments-service/pom.xml clean package spring-boot:repackage

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=build /build/payments-service/target/payments-service-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]