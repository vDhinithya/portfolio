# 1: Use an official Maven image to build the project
FROM maven:3.9.8-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy only the pom.xml and download dependencies first (for build cache efficiency)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the app
COPY src ./src
RUN mvn clean package -DskipTests

# 2: Use a lightweight JRE image for running the app
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/portfolio-0.0.1-SNAPSHOT.jar app.jar

# Expose the port defined in your application.yml
EXPOSE 4412

# Environment variables for email credentials (optional; better than committing them)
ENV SPRING_MAIL_USERNAME=""
ENV SPRING_MAIL_PASSWORD=""

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
