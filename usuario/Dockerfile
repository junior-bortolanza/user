FROM gradle:8.14-jdk21 AS BUILD

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

FROM openjdk:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar  /app/usuario.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/usuario.jar"]