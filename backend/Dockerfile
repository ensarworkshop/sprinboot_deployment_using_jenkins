# Use an official OpenJDK runtime as a parent image
FROM openjdk:20-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven build file first (for caching dependencies)
COPY pom.xml .

# Download dependencies without building the project
RUN apt-get update && \
    apt-get install -y maven && \
    mvn dependency:go-offline

# Copy the rest of the application source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Expose the application port
EXPOSE 8080

# Define environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
CMD ["java", "-jar", "target/shopease-0.0.1-SNAPSHOT.jar"]