FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

RUN ./gradlew build -x test || true

COPY . .

RUN ./gradlew clean bootJar -x test

# ──────────────────────────────
# ──────────────────────────────
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]
