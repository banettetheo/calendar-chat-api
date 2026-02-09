# Stage 1: Build
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app

# Copy maven wrapper and pom
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copy source and build
COPY src src
RUN ./mvnw package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Copy built JAR
COPY --from=build /app/target-maven/*.jar app.jar

# Web port
EXPOSE 8084
# RSocket port is also 8084 by default in this config (websocket)

ENTRYPOINT ["java", "-jar", "app.jar"]
