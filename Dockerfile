FROM openjdk:21-jdk
WORKDIR /app
COPY ./target/restful-export-import-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]