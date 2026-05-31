#FROM ubuntu:latest
#LABEL authors="Pranoti"
#
#ENTRYPOINT ["top", "-b"]


FROM eclipse-temurin:21
LABEL authors="Pranoti"

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]