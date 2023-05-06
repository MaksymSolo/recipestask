FROM openjdk:11-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven Wrapper script and make it executable
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

# Copy the Maven POM file and the source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN ./mvnw clean install

# Expose the port used by the application
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "target/recipesbook-service.jar"]
