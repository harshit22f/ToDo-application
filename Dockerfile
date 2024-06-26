# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file from the target directory to the working directory in the container
COPY target/todo-app-1.0-SNAPSHOT.jar /app/app.jar

# Copy the config file
COPY config.yml /app/config.yml

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar", "server", "/app/config.yml"]
