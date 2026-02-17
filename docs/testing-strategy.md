
# Estrategia de Pruebas


Niveles de Testing Implementados
Capa de Servicio (Unitaria): Se utiliza MockitoExtension para una ejecución ultrarrápida. Se valida la lógica de negocio y la interacción con el HuespedValidacionesHelper y el Mapper mediante @InjectMocks.

Capa Web (Integración): Se utiliza @SpringBootTest con MockMvc para validar el contrato REST. El uso de @MockitoBean (Spring 3.4+) permite simular errores de servicio para verificar que las respuestas HTTP (409, 400, etc.) sean correctas según el estándar diseñado.


## Evolución Tecnológica




### Modernización de Tests (Spring Boot 3.4+)
"Se ha migrado la infraestructura de pruebas para utilizar la nueva API de Bean Override. En lugar de la anotación obsoleta @MockBean, se implementó @MockitoBean, alineando el proyecto con los estándares actuales de Spring Framework y garantizando la compatibilidad con futuras versiones."


"Se utiliza Mockito para mockear no solo los Repositorios, sino también los Mappers y Helpers. Esto garantiza que las pruebas del HuespedService se centren exclusivamente en la orquestación del flujo de negocio, delegando la transformación de datos y las validaciones a sus respectivos mocks."

Estrategia de Pruebas de Unidad y Orquestación
"Se utiliza Mockito para mockear no solo los Repositorios, sino también los Mappers y Helpers. Esto garantiza que las pruebas del HuespedService se centren exclusivamente en la orquestación del flujo de negocio, delegando la transformación de datos y las reglas de validación externa a componentes simulados.

Esta arquitectura permite:

Aislamiento Total: Probar la lógica de registro sin depender de que el Mapper funcione (eso se prueba en su propio test).

Velocidad: Los tests unitarios no levantan el contexto de Spring, ejecutándose en milisegundos."

Pruebas de Integración con H2
"Para los componentes que requieren interacción con la base de datos, se utiliza @SpringBootTest junto con H2 In-Memory. Como se observa en los logs de ejecución, Flyway recrea el esquema en cada ejecución de la suite de tests, garantizando un entorno idempotente (siempre empieza igual) y libre de efectos colaterales entre pruebas."

Getty Images