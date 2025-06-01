# EntrenaSync Client Microservice

## Descripción

Este microservicio forma parte del ecosistema **EntrenaSync** y se encarga de la gestión completa de clientes del sistema. Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para el manejo de clientes, incluyendo información personal, servicios contratados y rutinas de entrenamiento asociadas.

## Tecnologías Utilizadas

-   **Kotlin** - Lenguaje de programación principal
-   **Spring Boot 3.x** - Framework de aplicación
-   **Spring Web** - Para la creación de API REST
-   **Spring Data MongoDB** - Integración con base de datos MongoDB
-   **Spring Cache** - Sistema de caché integrado
-   **MongoDB** - Base de datos NoSQL para persistencia
-   **Jakarta Validation** - Validación de datos de entrada
-   **Gradle (Kotlin)** - Gestión de dependencias y construcción del proyecto

## Características Principales

### Gestión de Clientes

-   ✅ Creación de nuevos clientes
-   ✅ Consulta de clientes por ID
-   ✅ Listado paginado de clientes
-   ✅ Actualización de información de clientes
-   ✅ Eliminación de clientes
-   ✅ Validación completa de datos de entrada
-   ✅ Manejo global de excepciones
-   ✅ Sistema de caché para optimización de rendimiento

### Modelos de Datos

-   **Client**: Información completa del cliente (datos personales, servicios contratados, rutinas)

## Estructura del Proyecto

```
src/main/kotlin/entrenasync/clientmicroservice/
├── Controllers/
│   └── ClientController.kt             # Endpoints REST
├── Dtos/
│   ├── ClientCreateRequest.kt          # DTO para creación
│   ├── ClientUpdateRequest.kt          # DTO para actualización
│   └── ClientResponse.kt               # DTO de respuesta
├── Models/
│   └── Client.kt                       # Entidad principal
├── Services/
│   ├── IClientService.kt              # Interfaz del servicio
│   └── ClientService.kt               # Implementación del servicio
├── Repositories/
│   └── IClientRepository.kt           # Repositorio de clientes
├── Mappers/
│   └── ClientMapper.kt                # Transformadores de datos
├── Exceptions/
│   ├── ClientException.kt             # Excepción base
│   └── ClientNotFoundException.kt     # Excepción específica
└── ExceptionHandler/
    └── GlobalExceptionHandler.kt      # Manejo global de errores
```

## API Endpoints

### Clients

| Método   | Endpoint        | Descripción                           |
| -------- | --------------- | ------------------------------------- |
| `GET`    | `/Clients`      | Obtener todos los clientes (paginado) |
| `GET`    | `/Clients/{id}` | Obtener cliente por ID                |
| `POST`   | `/Clients`      | Crear nuevo cliente                   |
| `PUT`    | `/Clients/{id}` | Actualizar cliente existente          |
| `DELETE` | `/Clients/{id}` | Eliminar cliente                      |

## Configuración

### Variables de Entorno

#### Desarrollo (application-dev.properties)

-   `MONGO_HOST`: Host de MongoDB (default: clients-microservice-mongo-db)
-   `MONGO_PORT`: Puerto de MongoDB (default: 27017)
-   `MONGO_DB`: Nombre de la base de datos (default: clients)
-   `DATABASE_USER`: Usuario de MongoDB (default: admin)
-   `DATABASE_PASSWORD`: Contraseña de MongoDB (default: adminPassword123)

#### Producción (application-prod.properties)

-   `MONGO_URI`: URI de conexión a MongoDB Atlas
-   `MONGO_DB`: Nombre de la base de datos (default: clients)

### Profiles de Spring

-   **dev**: Configuración para desarrollo local
-   **prod**: Configuración para producción

## Soporte

Para soporte técnico o preguntas, contacta con el equipo de desarrollo de EntrenaSync.
