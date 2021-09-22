FROM openjdk:18-jdk-alpine3.13
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 9090
CMD ["java", "-jar", "/app/app.jar"]