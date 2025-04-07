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

ENTRYPOINT ["java", "-jar", "app.jar"]