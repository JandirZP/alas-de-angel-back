# Alas de Angel - Sistema de Gestión Hospitalaria (Backend)

![Estado](https://img.shields.io/badge/Estado-En_Desarrollo-green)
![Java](https://img.shields.io/badge/Backend-Java_Spring_Boot-007396)
![Supabase](https://img.shields.io/badge/Database-Supabase-3ECF8E)

## Descripción del Proyecto

**Alas de Angel** es un sistema full-stack diseñado para digitalizar y optimizar la gestión de atención médica en centros de salud. Este repositorio contiene el código del **Backend**, el cual sirve como núcleo de la plataforma, proveyendo la API RESTful, gestionando la lógica de negocio, la seguridad (autenticación y autorización) y la persistencia de datos.

## Repositorios del Proyecto

El código fuente se encuentra dividido en dos repositorios para mantener una arquitectura limpia:

- **Backend (Spring Boot):** [alas-de-angel-back](https://github.com/JandirZP/alas-de-angel-back) *(Este repositorio)*
- **Frontend (React + Vite):** [alas-de-angel-front](https://github.com/JandirZP/alas-de-angel-front)

## Entorno de Producción (Despliegue)

El backend de la aplicación se encuentra en producción y accesible a través de los siguientes servicios:

- **Backend (API):** Desplegado en **Render**.
- **Base de Datos:** Alojada y gestionada en **Supabase** (PostgreSQL).

## Stack Tecnológico Principal

- **Lenguaje:** Java (JDK 17+)
- **Framework:** Spring Boot
- **Seguridad:** Spring Security con JSON Web Tokens (JWT)
- **Persistencia de Datos:** Spring Data JPA / Hibernate
- **Gestión de Dependencias:** Maven
- **Base de Datos:** PostgreSQL (Vía Supabase)

## Instalación y Configuración Local

Sigue estos pasos para levantar el entorno de desarrollo de la API REST en tu máquina local.

### Prerrequisitos
- [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/) (Opcional, puedes usar el wrapper `./mvnw` incluido).
- Credenciales de la base de datos de Supabase.

### Pasos

1. **Clonar este repositorio:**
   ```bash
   git clone https://github.com/JandirZP/alas-de-angel-back.git
   ```

2. **Ingresar al directorio:**
   *(Ajusta el nombre de la carpeta si en local lo tienes diferente, por ejemplo `gestion-pacientes-back`)*
   ```bash
   cd alas-de-angel-back
   ```

3. **Configurar Variables de Entorno:**
   Revisa el archivo `src/main/resources/application.properties` (o `application.yml`, o archivo `.env` si usas uno) y asegúrate de configurar correctamente:
   - Las credenciales de conexión a la base de datos Supabase (URL, usuario, contraseña).
   - El secreto para firmar los tokens (`JWT_SECRET`).

4. **Ejecutar el servidor:**
   Utiliza el wrapper de Maven para iniciar la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

Una vez que Spring Boot inicie correctamente, el servidor estará escuchando generalmente en el puerto `8080` (`http://localhost:8080`).

## Autenticación

El sistema está asegurado mediante JWT:
- Se expone un endpoint público para el inicio de sesión (`/auth/login` o similar).
- Para consumir los endpoints protegidos, debes incluir el token en la cabecera de la petición HTTP:
  `Authorization: Bearer <tu_token_jwt>`
