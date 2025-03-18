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
# Etapa de ejecución (aquí van los cambios)
# --------------------------------------------
FROM openjdk:21 AS run

# Instalar dependencias y certificado SSL
RUN apt-get update && apt-get install -y wget
RUN wget https://letsencrypt.org/certs/isrgrootx1.pem -P /tmp/ && \
    keytool -importcert -alias ISRGRootCA -file /tmp/isrgrootx1.pem -keystore /etc/ssl/certs/java/cacerts -storepass changeit -noprompt

WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/my-app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app/my-app.jar"]