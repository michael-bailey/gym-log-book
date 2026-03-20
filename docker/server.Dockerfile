FROM gradle:8.11.1-jdk21 AS builder

WORKDIR /workspace

COPY gradle gradle
COPY gradlew gradlew
COPY gradlew.bat gradlew.bat
COPY settings.gradle.kts build.gradle.kts gradle.properties ./
COPY gym_log_book_server gym_log_book_server
COPY gym_log_book_shared gym_log_book_shared

RUN chmod +x gradlew && ./gradlew :gym_log_book_server:buildFatJar --no-daemon

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/gym_log_book_server/build/libs/*-all.jar /app/gym-log-book-server.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/gym-log-book-server.jar"]
