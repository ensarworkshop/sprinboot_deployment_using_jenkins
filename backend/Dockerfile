
FROM openjdk:20-jdk-slim


WORKDIR /app


COPY pom.xml .


RUN apt-get update && \
    apt-get install -y maven && \
    mvn dependency:go-offline


COPY src ./src


RUN mvn clean package -DskipTests


EXPOSE 8080


ENV SPRING_PROFILES_ACTIVE=prod


CMD ["java", "-jar", "target/shopease-0.0.1-SNAPSHOT.jar"]