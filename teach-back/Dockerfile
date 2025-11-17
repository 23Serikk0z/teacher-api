# Стадия сборки
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Копируем Gradle wrapper и конфиги (пути относительные к контексту!)
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle

RUN chmod +x gradlew
RUN ./gradlew build -x test || true

# Копируем весь исходный код
COPY . .

RUN ./gradlew clean bootJar -x test

# ──────────────────────────────
# Финальный образ
# ──────────────────────────────
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java","-jar","app.jar"]
