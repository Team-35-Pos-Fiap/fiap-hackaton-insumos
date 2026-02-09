FROM maven:3.9.6-eclipse-temurin-21-jammy AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .
RUN mvn clean install

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
EXPOSE 8088
COPY --from=builder /app/target/*.jar insumos.jar
ENTRYPOINT ["java", "-jar", "insumos.jar"]