FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY pom.xml ./pom.xml
COPY restaurants-service/pom.xml restaurants-service/pom.xml

RUN mvn -q -B -DskipTests -f restaurants-service/pom.xml dependency:go-offline

COPY restaurants-service/src restaurants-service/src

RUN mvn -q -B -DskipTests -f restaurants-service/pom.xml clean package spring-boot:repackage

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=build /build/restaurants-service/target/restaurants-service-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]