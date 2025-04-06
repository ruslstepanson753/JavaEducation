FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
COPY src src

RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar
COPY --from=builder /app/src/main/resources/questions /app/questions

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]