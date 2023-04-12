FROM maven:3.8.4-openjdk-17-slim as build

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src

RUN sed -i 's/\r$//' mvnw
RUN --mount=type=cache,target=/root/.m2,rw mvn package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim-buster

COPY --from=build /target/shelf-life-rest-api-0.0.1-SNAPSHOT.jar /usr/app/shelf-life-rest-api-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","/usr/app/shelf-life-rest-api-0.0.1-SNAPSHOT.jar"]
