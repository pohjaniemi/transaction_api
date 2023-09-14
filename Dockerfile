# First build stage: Build the jar with Maven
FROM maven:3.9.4-amazoncorretto-17 AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean package

# Second build stage: Run the jar with Amazon Corretto 17 JDK on Alpine
FROM amazoncorretto:17-alpine-jdk
COPY --from=MAVEN_BUILD /target/transactions_api-SNAPSHOT.jar /app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app.jar"]