FROM gradle:8.11.1-jdk21 AS builder

WORKDIR /workspace

COPY gradle gradle
COPY gradlew gradlew
COPY gradlew.bat gradlew.bat
COPY settings.gradle.kts build.gradle.kts gradle.properties ./
COPY gym_log_book_client gym_log_book_client
COPY gym_log_book_shared gym_log_book_shared

RUN apt-get update && apt-get install -y libatomic1 && rm -rf /var/lib/apt/lists/*

RUN chmod +x gradlew && ./gradlew :gym_log_book_client:wasmJsBrowserDistribution --no-daemon

FROM nginx:1.27-alpine

COPY docker/web.nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /workspace/gym_log_book_client/build/dist/wasmJs/productionExecutable/ /usr/share/nginx/html/

EXPOSE 80
