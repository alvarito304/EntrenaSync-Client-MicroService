# Etapa de compilación, un docker especifico, que se etiqueta como build
FROM gradle:jdk21-alpine AS build

# Directorio de trabajo
WORKDIR /app

# Copia los archivos build.gradle y src de nuestro proyecto
COPY build.gradle.kts .
COPY gradlew .
COPY gradle gradle
COPY src src
#COPY despliegueServers despliegueServers
RUN chmod +x gradlew
RUN ./gradlew clean build
# RUN ./gradlew javadoc

FROM openjdk:21 AS run

# Instalar wget y certificado SSL
RUN apk update && apk add --no-cache wget
RUN wget https://letsencrypt.org/certs/isrgrootx1.pem -P /tmp/ && \
    keytool -importcert -alias ISRGRootCA -file /tmp/isrgrootx1.pem \
    -keystore $JAVA_HOME/lib/security/cacerts \  # Ruta correcta en Alpine
    -storepass changeit -noprompt
# Directorio de trabajo
WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/my-app.jar
#COPY --from=build /app/build/docs /app/doc
#COPY --from=build /app/despliegueServers /app/despliegueServers
#COPY --from=build /app/build/jacoco /app/jacoco
#COPY --from=build /app/build/reports/tests /app/tests
# Expone el puerto 3000
EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app/my-app.jar"]