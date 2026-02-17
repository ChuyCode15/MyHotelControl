# 🏨 MyHotelControl - Backend System

> Sistema robusto de gestión hotelera desarrollado con Spring Boot, enfocado en escalabilidad y buenas prácticas de ingeniería de software.

---

## 📍 Índice
1. [Sobre el Proyecto](#-sobre-el-proyecto)
2. [Arquitectura del Sistema](#-arquitectura-del-sistema)
3. [Flujo del Backend](#-flujo-del-backend)
4. [Stack Tecnológico](#-stack-tecnológico)
5. [Log de Construcción y TDD](#-log-de-construcción-y-tdd)
6. [Cómo Ejecutar](#-cómo-ejecutar)
7. [Calidad y Testing](#-calidad-y-testing)

---

## 📖 Sobre el Proyecto
Este proyecto nace de la necesidad de gestionar de manera eficiente el flujo de huéspedes y reservas. A diferencia de sistemas educativos básicos, **MyHotelControl** implementa validaciones de negocio rigurosas y una capa de persistencia desacoplada.

---

## 🏗️ Arquitectura del Sistema
El proyecto sigue una **Arquitectura en Capas (Layered Architecture)** para garantizar el mantenimiento a largo plazo:

* **Controller Layer**: Gestión de endpoints REST y manejo de protocolos HTTP.
* **Service Layer**: Orquestación de la lógica de negocio (Donde reside la "inteligencia").
* **Domain/Entity Layer**: Modelado de datos y reglas de dominio.
* **Repository Layer**: Abstracción de la base de datos mediante Spring Data JPA.
* **Infrastructure/Utils**: Helpers de validación, mappers y configuraciones globales.



---

## 🔄 Flujo del Backend
Para el proceso de **Registro de Huésped**, el flujo de datos es el siguiente:

1. El Cliente envía un `JSON` -> Validado por `Jakarta Validation` en el **DTO**.
2. El **Controller** recibe el DTO y lo pasa al **Service**.
3. El **Service** usa el `ValidacionesHelper` para reglas de negocio (ej. evitar duplicados).
4. El `HuespedMapper` transforma el DTO en una **Entidad**.
5. El **Repository** persiste la entidad en la DB.
6. Se retorna un `201 Created` con la URI del nuevo recurso.

---

## 🛠️ Stack Tecnológico
* **Core:** Java 17+, Spring Boot 3.2.
* **Persistencia:** Hibernate, Spring Data JPA.
* **Productividad:** Lombok, MapStruct.
* **Calidad:** JUnit 5, Mockito (TDD approach).

---

## 🚀 Log de Construcción y TDD
Se documenta el progreso por hitos técnicos:

[x] Backend robusto: Endpoint de registro con validación de unicidad por idCard.

[x] Manejo de Errores: Implementación de GlobalExceptionHandler para retornar HTTP 409 en duplicados.

[x] Pruebas de Calidad: Cobertura de tests unitarios y de integración para el flujo de Huéspedes.


### Hito 1: Módulo de Huéspedes
- [x] Implementación de Entidad y DTOs.
- [x] Lógica de validación de identidad (Helper).
- [x] **Prueba de Integración**: Registro exitoso (Status 201).
- [x] **Prueba Unitaria**: Fallo por documento duplicado (Manejo de excepciones).
- [x] Prueba de Integración: Registro exitoso / Manejo de conflictos.
- [x] Prueba Unitaria: Orquestación del Service.

### Hito 2: Infraestructura Base Completada
- [x] Persistencia: Capa JPA operativa con H2 y validación de esquema.
- [x] Migraciones: Flyway gestionando el versionamiento del esquema (v1).
- [x] Endpoints: Registro de huéspedes funcional con retorno de código 201 Created.
- [x] Calidad: Suite de pruebas configurada con las últimas APIs de Spring Boot 3.4+.

---

## 💻 Cómo Ejecutar

🛠️ Guía de Configuración (Setup)
Configuración del Entorno (YAML)
El proyecto utiliza archivos YAML para una configuración más legible y jerárquica. La base de datos de desarrollo está configurada como jdbc:h2:mem:hotel_db, lo que garantiza que no queden datos residuales entre sesiones de trabajo.

Solución de Errores Comunes (Troubleshooting)
Schema-validation missing table: Este error ocurre si Hibernate intenta validar el esquema antes de que Flyway termine. Se soluciona asegurando que los scripts SQL tengan la nomenclatura V<N>__nombre.sql (con doble guion bajo) y configurando la inicialización diferida del DataSource.

Missing Mapper Bean: Resuelto mediante la correcta configuración del maven-compiler-plugin en el pom.xml, asegurando el orden: 1. Lombok -> 2. Binding -> 3. MapStruct.

---
## 🧪 Calidad y Testing

Modernización (Spring Boot 3.4+)
Se ha migrado la infraestructura de pruebas para utilizar la nueva API de Bean Override. En lugar de la anotación obsoleta @MockBean, se implementó @MockitoBean, alineando el proyecto con los estándares actuales de Spring Framework y garantizando la compatibilidad con futuras versiones.

Estrategia de Pruebas de Unidad
Se utiliza Mockito para mockear no solo los Repositorios, sino también los Mappers y Helpers. Esto garantiza que las pruebas del HuespedService se centren exclusivamente en la orquestación del flujo de negocio, delegando la transformación de datos y el acceso a base de datos a componentes simulados. Esto reduce el acoplamiento y aumenta la velocidad de ejecución de la suite de pruebas.

In-Memory Testing (Integración)
Para los tests de controladores (@WebMvcTest o @SpringBootTest), se adoptó una estrategia de Base de Datos Efímera. Se utiliza el perfil test que levanta una instancia de H2, permitiendo validar el comportamiento de los Endpoints y el GlobalExceptionHandler sin necesidad de una base de datos física, asegurando un entorno de pruebas limpio en cada ejecución.

---