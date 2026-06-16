# Start with a base image containing Java 21 runtime
FROM eclipse-temurin:21-jre-alpine

# Add Maintainer Info
LABEL maintainer="john@dobie.email"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available outside this container
EXPOSE 8080

# The application's executable boot jar (exclude the -plain jar)
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
