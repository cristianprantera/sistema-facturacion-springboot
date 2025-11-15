# Sistema de Facturación – API REST (Spring Boot + PostgreSQL + Docker + JWT + Swagger)

Proyecto backend desarrollado con **Spring Boot**, **Java 23**, **PostgreSQL**, **Docker**, **Spring Security (JWT)** y **Swagger/OpenAPI**, que implementa un sistema completo de facturación: gestión de **productos**, **clientes**, **facturas** y **usuarios**, con control de roles (**ADMIN** / **EMPLEADO**) y autenticación mediante tokens JWT.

El proyecto está totalmente containerizado, documentado y listo para ser ejecutado de manera reproducible por cualquier reclutador o equipo técnico.

---

## 🧩 Tecnologías principales

* **Java 23**
* **Spring Boot 3.5**
* **Spring Security + JWT**
* **Spring Data JPA**
* **PostgreSQL 16**
* **Swagger / OpenAPI**
* **JUnit 5 + Mockito**
* **Lombok**
* **Docker & Docker Compose**
* Arquitectura en capas (controller, service, repository, dto, entity)

---

## 📦 Funcionalidades

* **Autenticación JWT**
* **Seeder automático de usuarios**:

  * `admin12345` → Rol **ADMIN**
  * `empleado12345` → Rol **EMPLEADO**
* CRUD completo de:

  * **Clientes**
  * **Productos**
  * **Facturas** (incluye detalles y actualización automática de stock)
* **Paginación**
* **Validaciones exhaustivas con @Valid**
* **Manejo global de excepciones**
* **Documentación interactiva con Swagger**
* **Configuración externa mediante variables de entorno**
* **Tests unitarios con Mockito** (ProductoController y ProductoService)

---

# 🚀 Cómo ejecutar el proyecto

## 1️⃣ Requisitos

* Docker y Docker Compose instalados
* Maven instalado (solo si desea recompilar el JAR manualmente)

---

# ▶️ Ejecución rápida (modo reclutador)

Simplemente ejecutar:

```bash
docker compose up --build
```

Esto levantará automáticamente:

* **Backend** → [http://localhost:8080](http://localhost:8080)
* **Swagger UI** → [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* **Adminer (visualización DB)** → [http://localhost:8081](http://localhost:8081)
* **PostgreSQL** en puerto **5433**

---

# 🔐 Usuarios creados automáticamente

El sistema genera dos usuarios al iniciarse por primera vez:

| Usuario       | Contraseña    | Rol      |
| ------------- | ------------- | -------- |
| admin12345    | admin12345    | ADMIN    |
| empleado12345 | empleado12345 | EMPLEADO |

---

# 📘 Documentación de API

Swagger UI:

👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

Endpoints públicos:

* `POST /api/v1/auth/login`

Endpoints privados (requieren JWT):

* `/api/v1/clientes/**`
* `/api/v1/productos/**`
* `/api/v1/facturas/**`

El token se envía en los headers:

```
Authorization: Bearer <token>
```

---

# 🧪 Tests Automatizados (JUnit + Mockito)

El proyecto incluye tests unitarios básicos utilizando **JUnit 5** y **Mockito**.

Se testearon específicamente:

* **ProductoService**: lógica de negocio, validaciones y flujo de CRUD mockeando el repositorio.
* **ProductoController**: respuestas HTTP y mapping usando MockMvc, mockeando el service.

Ejecución de tests:

```bash
mvn test
```

Ubicación:

```
src/test/java/com/facturacion/sistema/
```

---

# 🐳 Docker

## docker-compose.yml

```yaml
version: "3.8"

services:
  postgres:
    image: postgres:16
    container_name: postgres_facturacion
    environment:
      POSTGRES_DB: facturaciondb
      POSTGRES_USER: cristian
      POSTGRES_PASSWORD: 1234
    ports:
      - "5433:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data
    networks:
      - factu-net

  adminer:
    image: adminer
    container_name: adminer_facturacion
    ports:
      - "8081:8080"
    networks:
      - factu-net

  backend:
    build: .
    container_name: backend_facturacion
    depends_on:
      - postgres
    environment:
      DB_URL: jdbc:postgresql://postgres:5432/facturaciondb
      USERNAME: cristian
      PASSWORD: 1234
      JWT_SECRET: mi_super_clave_secreta_para_jwt_123456789
    ports:
      - "8080:8080"
    networks:
      - factu-net

networks:
  factu-net:

volumes:
  pgdata:
```

---

## 🏗️ Dockerfile

```dockerfile
FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

# ⚙️ Configuración (application.yml)

```yaml
spring:
  application:
    name: SistemaDeFacturacion

  datasource:
    url: ${DB_URL}
    username: ${USERNAME}
    password: ${PASSWORD}

  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
    properties:
      dialect: org.hibernate.dialect.PostgreSQLDialect  

  jwt:
    secret: ${JWT_SECRET}
    expiration-ms: 3600000
```

---

# 📑 Compilar manualmente (opcional)

```bash
mvn clean package -DskipTests
```

El .jar se genera en:

```
/target/sistema-0.0.1-SNAPSHOT.jar
```

---

# 🗄️ Acceso a la Base de Datos (Adminer)

Adminer corre en:

👉 [http://localhost:8081](http://localhost:8081)

Datos de conexión:

* **System:** PostgreSQL
* **Server:** postgres
* **User:** cristian
* **Password:** 1234
* **Database:** facturaciondb

---

# 🏁 Estado del Proyecto

Sistema completamente funcional, seguro y listo para uso real o evaluación técnica.
Incluye buenas prácticas de arquitectura, seguridad, testing y despliegue mediante contenedores.

---

# 👤 Autor

**Cristian Prantera**
LinkedIn: [https://www.linkedin.com/in/cristianprantera](https://www.linkedin.com/in/cristianprantera)
