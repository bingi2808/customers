# 🏗 Build stage
FROM amazoncorretto:21 AS build
WORKDIR /app

# Copy source code
COPY . .

# Build the application
RUN ./gradlew build --no-daemon

# Use JLink to generate a minimal runtime
RUN $JAVA_HOME/bin/jlink --compress=2 --module-path $JAVA_HOME/jmods \
    --add-modules java.base,java.logging,java.sql \
    --output /custom-jre

# 🚀 Final runtime stage (Amazon Corretto, not Alpine)
FROM amazoncorretto:21
WORKDIR /app

# Copy the custom JRE and application JAR from the build stage
COPY --from=build /custom-jre /custom-jre
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Run the application using the custom JRE
ENTRYPOINT ["/custom-jre/bin/java", "-jar", "/app/app.jar"]
