# TaskFlow REST API

API REST construida con **Java 17**, **Spring Boot 3.1.1**, **Spring Security 6 (JWT)**, **Spring Data JPA**, **MySQL**, **Lombok** y **Swagger (OpenAPI 3)**.

---

## 🚀 Requisitos Previos

- **Java 17 JDK** o superior instalado.
- **Maven 3.8+** (o usar el wrapper de tu IDE).
- Servidor **MySQL** corriendo en local (puerto por defecto `3306`).

---

## ⚙️ Configuración de Base de Datos

En el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/task_flow_2?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=tu_password_aqui
```

> **Nota:** La base de datos es:
> ```sql
> CREATE DATABASE IF NOT EXISTS task_flow_2;
> USE task_flow_2;
> ```
> Las tablas (`users`, `tasks`, `user_roles`) se crearán automáticamente gracias a `hibernate.ddl-auto: update`.

---

## 🏃‍♂️ Ejecución del Proyecto

Desde la raíz del proyecto en tu terminal:

```bash
mvn spring-boot:run
```

O abre la carpeta en tu IDE favorito (IntelliJ IDEA, Eclipse, VS Code) y ejecuta `TaskFlowApplication.java`.

---

## 📚 Documentación Interactiva (Swagger / OpenAPI)

Una vez iniciado el servidor, ingresa a:
- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### ¿Cómo probar endpoints protegidos en Swagger?
1. Realiza el registro en `POST /api/v1/auth/register`.
2. Haz login en `POST /api/v1/auth/login` y copia el `token`.
3. Haz clic en el botón verde **Authorize** arriba a la derecha en Swagger UI e introduce el token.
4. ¡Listo! Ya podrás ejecutar cualquier endpoint de `/api/v1/tasks`.

---

## 📁 Arquitectura del Proyecto

```text
src/main/java/com/devioz/taskflow/
├── TaskFlowApplication.java        # Punto de entrada
├── config/                         # Swagger, CORS y Auditoría JPA
├── controller/                     # Controladores REST (@RestController)
├── exception/                      # Manejador global de errores (@RestControllerAdvice)
├── model/                          # Entidades JPA (@Entity) y BaseEntity
├── payload/                        # DTOs (Request / Response)
├── repository/                     # Repositorios Spring Data JPA
├── security/                       # Configuración Spring Security, JWT y Filtros
└── service/                        # Lógica de negocio (Interfaces e Implementaciones)
```
