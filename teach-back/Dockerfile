# Стадия сборки
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Копируем Gradle wrapper и конфиги
COPY teach-back/gradlew teach-back/gradle ./gradle/
COPY teach-back/build.gradle teach-back/settings.gradle ./

RUN chmod +x gradlew
RUN ./gradlew build -x test || true

# Копируем весь исходный код
COPY teach-back/. .

RUN ./gradlew clean bootJar -x test

# Стадия runtime
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java","-jar","app.jar"]