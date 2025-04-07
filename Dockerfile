FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar
COPY src/main/resources/questions /app/questions/
COPY src/main/resources/templates /app/templates/

EXPOSE 8080

# Установите переменные окружения по умолчанию
ENV COM_JAVARUSH_QUESTIONSPATH=/app/questions
ENV DATABASE_URL=postgresql://postgres:XoDAAoldFEoPqagDVzCeHDpiyWmfVvpj@tramway.proxy.rlwy.net:59100/railway
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=XoDAAoldFEoPqagDVzCeHDpiyWmfVvpj
ENV REDISHOST=redis.railway.internal

ENTRYPOINT ["java", "-jar", "app.jar"]