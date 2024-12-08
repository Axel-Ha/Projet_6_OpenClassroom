FROM maven:3-openjdk-17-slim as build

RUN mkdir -p /workspace

WORKDIR /workspace

COPY pom.xml /workspace

COPY src /workspace/src

RUN mvn -f pom.xml clean package -DskipTests

FROM openjdk:17-alpine

COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 3001

ENTRYPOINT [ "java", "-jar", "/app.jar" ]
