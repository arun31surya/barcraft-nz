# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom and source
COPY pom.xml .
COPY src ./src

# Build the jar (skip tests to speed up)
RUN mvn -q -DskipTests clean package

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the jar from the build image
COPY --from=build /app/target/*.jar app.jar

# Spring Boot listens on 8080 by default
EXPOSE 8080

# Start the app
ENTRYPOINT ["java","-jar","app.jar"]