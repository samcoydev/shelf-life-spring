FROM maven:3.8.4-openjdk-17-slim as build

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src

RUN sed -i 's/\r$//' mvnw
RUN --mount=type=cache,target=/root/.m2,rw mvn package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim-buster

COPY --from=build /target/shelf-life-rest-api-0.0.1-SNAPSHOT.jar /usr/app/shelf-life-rest-api-0.0.1-SNAPSHOT.jar

USER root
COPY coycafe-ddns-net.pem $JAVA_HOME/lib/security
RUN cd $JAVA_HOME/lib/security \
    && keytool -importcert -file coycafe-ddns-net.pem -alias coycafe-ddns-net -keystore cacerts -storepass changeit -noprompt -trustcacerts


ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","/usr/app/shelf-life-rest-api-0.0.1-SNAPSHOT.jar"]
