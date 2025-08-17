# Build with Java 21
FROM eclipse-temurin:21-jre as runtime
WORKDIR /app
# Copy the fat jar built by Spring Boot
COPY target/kafka-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9191

ENTRYPOINT ["java","-XX:+UseContainerSupport","-jar","/app/app.jar"]
