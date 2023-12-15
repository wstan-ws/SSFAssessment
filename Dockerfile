FROM maven:3-eclipse-temurin-21 AS builder

WORKDIR /app

COPY src src
COPY .mvn .mvn
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-bookworm

WORKDIR /app2

COPY --from=builder /app/target/eventmanagement-0.0.1-SNAPSHOT.jar app.jar
COPY events.json .

ENV PORT=8080
ENV SPRING_REDIS_HOST=localhost
ENV SPRING_REDIS_PORT=6379
ENV SPRING_REDIS_USERNAME=default
ENV SPRING_REDIS_PASSWORD=abc123

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar