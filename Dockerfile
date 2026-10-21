# syntax=docker/dockerfile:1

FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace

COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw -B -q dependency:go-offline

COPY src src
RUN ./mvnw -B -q package -DskipTests \
    && java -Djarmode=tools -jar target/*.jar extract --layers --launcher --destination target/extracted

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S simulator && adduser -S simulator -G simulator

COPY --from=build --chown=simulator:simulator /workspace/target/extracted/dependencies/ ./
COPY --from=build --chown=simulator:simulator /workspace/target/extracted/spring-boot-loader/ ./
COPY --from=build --chown=simulator:simulator /workspace/target/extracted/snapshot-dependencies/ ./
COPY --from=build --chown=simulator:simulator /workspace/target/extracted/application/ ./

USER simulator

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError"

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher"]
