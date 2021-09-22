FROM openjdk:18-jdk-alpine3.13
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
WORKDIR /app
COPY . .
RUN ./mvnw package
CMD ["java", "-jar", "/target/blogapp.jar"]
EXPOSE 9090