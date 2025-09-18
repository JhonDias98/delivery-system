FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY pom.xml ./pom.xml
COPY delivery-service/pom.xml delivery-service/pom.xml

RUN mvn -q -B -DskipTests -f delivery-service/pom.xml dependency:go-offline

COPY delivery-service/src delivery-service/src

RUN mvn -q -B -DskipTests -f delivery-service/pom.xml clean package spring-boot:repackage

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=build /build/delivery-service/target/delivery-service-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]