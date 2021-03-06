# Start with a base image containing Java runtime
FROM openjdk:8u212-jdk-alpine

# Add Maintainer Info
LABEL maintainer="john@dobie.email"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/*.jar

# Add the application's jar to the container
ADD ${JAR_FILE} spring-boot-example.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/spring-boot-example.jar"]