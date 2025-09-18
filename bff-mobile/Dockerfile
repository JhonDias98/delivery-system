FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY pom.xml ./pom.xml
COPY bff-mobile/pom.xml bff-mobile/pom.xml

RUN mvn -q -B -DskipTests -f bff-mobile/pom.xml dependency:go-offline

COPY bff-mobile/src bff-mobile/src

RUN mvn -q -B -DskipTests -f bff-mobile/pom.xml clean package spring-boot:repackage

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=build /build/bff-mobile/target/bff-mobile-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]