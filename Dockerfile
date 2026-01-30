# -------- Build stage --------
FROM gradle:8-jdk21-alpine AS build
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY doc ./doc
COPY .shell ./.shell
RUN chmod +x gradlew
RUN ./gradlew openApiGenerate --no-daemon
RUN ./gradlew publishGeneratedToMavenLocal --no-daemon
RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew bootJar --no-daemon

# -------- Runtime stage --------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

ENV PORT=8080

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE ${PORT}

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
