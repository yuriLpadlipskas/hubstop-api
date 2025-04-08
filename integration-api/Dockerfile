FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -DskipTests -f /usr/src/app/pom.xml clean package

FROM openjdk:21-slim

COPY --from=build /usr/src/app/target/*.jar app.jar

EXPOSE 9091

ENTRYPOINT ["java","-jar","/app.jar"]
