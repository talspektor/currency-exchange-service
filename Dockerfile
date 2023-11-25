FROM openjdk:11-jre-slim
EXPOSE 8100
ADD /target/currency-exchange-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]