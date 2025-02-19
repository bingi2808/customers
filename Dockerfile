# Build stage
FROM amazoncorretto:21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew build --no-daemon

# Final runtime stage (NO jlink)
FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]