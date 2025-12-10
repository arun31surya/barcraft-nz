# ---------- Build stage ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy EVERYTHING into the build container
# (pom.xml, src, .mvn, mvnw, etc)
COPY . .

# Build the jar (skip tests so Spring's default test doesn't break the build)
RUN mvn -q -DskipTests clean package

# ---------- Run stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Spring Boot uses 8080
EXPOSE 8080

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]