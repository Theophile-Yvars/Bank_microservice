# Base image with JDK and Maven
FROM maven:3.6.3-openjdk-17-slim AS build

# Copy the source code
COPY . /app

# Set the working directory
WORKDIR /app

# Build the application

# Final image with only the JAR file and JRE
FROM openjdk:17

# Set the working directory
WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
