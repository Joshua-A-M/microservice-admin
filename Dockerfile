# ------------ Stage 1: Build Layer ------------
FROM eclipse-temurin:21-jdk as build

# Build argument for the jar path (relative to build context)
ARG JAR_FILE=build/libs/admin-0.0.1-SNAPSHOT.jar

# Copy the fat jar built by Gradle into the container
COPY ${JAR_FILE} app.jar

# Unpack the jar to target/dependency
RUN mkdir -p target/dependency && \
    cd target/dependency && \
    jar -xf /app.jar

# ------------ Stage 2: Runtime Layer ------------
FROM eclipse-temurin:21-jdk

# Volume for temporary files (optional)
VOLUME /tmp

# Working directory for the app
WORKDIR /app

# Copy unpacked contents from build stage
ARG DEPENDENCY=target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Set the entrypoint to your Spring Boot main class
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.chatservice.admin.EmployeeserviceApplication"]
