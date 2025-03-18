# Etapa de compilación (usa imagen válida)
FROM gradle:8.7.0-jdk21-alpine AS build

WORKDIR /app
COPY build.gradle.kts .
COPY gradlew .
COPY gradle gradle
COPY src src
RUN chmod +x gradlew
RUN ./gradlew clean build

# --------------------------------------------
# Etapa de ejecución (imagen verificada)
# --------------------------------------------
FROM eclipse-temurin:21.0.3_9-jdk-alpine AS run

# Instalar dependencias y certificado (ruta absoluta)
RUN apk update && apk add --no-cache wget
RUN wget https://letsencrypt.org/certs/isrgrootx1.pem -P /tmp/ && \
    keytool -importcert -alias ISRGRootCA -file /tmp/isrgrootx1.pem \
    -keystore /opt/java/openjdk/lib/security/cacerts \
    -storepass changeit -noprompt

WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/my-app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app/my-app.jar"]