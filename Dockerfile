# Etapa de compilación (usa imagen Gradle con JDK 21)
FROM gradle:7.6.2-jdk21-alpine AS build

WORKDIR /app
COPY build.gradle.kts .
COPY gradlew .
COPY gradle gradle
COPY src src
RUN chmod +x gradlew
RUN ./gradlew clean build

# --------------------------------------------
# Etapa de ejecución (usa imagen Alpine corregida)
# --------------------------------------------
FROM eclipse-temurin:21-jdk-alpine AS run

RUN apk update && apk add --no-cache wget
RUN wget https://letsencrypt.org/certs/isrgrootx1.pem -P /tmp/ && \
    keytool -importcert -alias ISRGRootCA -file /tmp/isrgrootx1.pem \
    -keystore $JAVA_HOME/lib/security/cacerts \
    -storepass changeit -noprompt

WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/my-app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app/my-app.jar"]