FROM eclipse-temurin:21
LABEL authors="Pranoti"

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]