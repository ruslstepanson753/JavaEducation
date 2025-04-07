FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar
COPY src/main/resources/questions /app/questions/

EXPOSE 8080

# Установите переменные окружения по умолчанию
ENV COM_JAVARUSH_QUESTIONSPATH=/app/questions
ENV DATABASE_URL=jdbc:postgresql://host.docker.internal:5432/postgres
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV REDISHOST=host.docker.internal

ENTRYPOINT ["java", "-jar", "app.jar"]