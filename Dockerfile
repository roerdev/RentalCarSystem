FROM maven:3.9.7-eclipse-temurin-22 AS maven
WORKDIR /app
COPY . /app
RUN mvn clean package

FROM openjdk:22-jdk-slim AS runtime
WORKDIR /app
COPY --from=maven /app/target/*.jar /app/
EXPOSE 8081
CMD find *.jar -exec java -jar -XshowSettings:vm {} ';'