# Etapa de compilación
FROM gradle:jdk21-alpine AS build

WORKDIR /app
COPY build.gradle.kts .
COPY gradlew .
COPY gradle gradle
COPY src src
RUN chmod +x gradlew
RUN ./gradlew clean build

# --------------------------------------------
# Etapa de ejecución (Alpine)
# --------------------------------------------
FROM openjdk:21-alpine AS run

# Instalar dependencias y certificado (ruta corregida)
RUN apk update && apk add --no-cache wget
RUN wget https://letsencrypt.org/certs/isrgrootx1.pem -P /tmp/ && \
    keytool -importcert -alias ISRGRootCA -file /tmp/isrgrootx1.pem \
    -keystore /usr/lib/jvm/java-21-openjdk/lib/security/cacerts \
    -storepass changeit -noprompt

WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/my-app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app/my-app.jar"]