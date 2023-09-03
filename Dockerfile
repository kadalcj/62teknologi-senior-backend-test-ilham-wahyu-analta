# Official OpenJDK 17
FROM openjdk:17-jdk-alpine

# Create a directory to store your application files
RUN mkdir /app

# Copy Spring Boot App Jar File into container
COPY build/libs/sbe-test-0.0.1-SNAPSHOT.jar app/app.jar

# Expose the Spring Boot App port
EXPOSE 8080

# Command to run Spring Boot App
CMD ["java", "-jar", "app/app.jar"]
