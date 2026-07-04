# Evidencia de uso de GitHub Copilot — spring-batch-final-calificaciones

> Todos los prompts se hicieron desde el **Chat de Copilot**, no por autocompletado inline.

## Prompt 1 — pom.xml

- **Prompt:**
  ```
  Genera un pom.xml para un proyecto Spring Boot 3.2.2 con Java 17 y estas dependencias:
  spring-boot-starter-batch, mysql-connector-j (scope runtime), spring-boot-starter-data-mongodb,
  spring-boot-starter-web, spring-boot-starter-data-jpa y spring-boot-starter-test (scope test).
  groupId com.academia, artifactId spring-batch-final-calificaciones, versión 1.0.0.
  Incluye el spring-boot-maven-plugin.
  ```
- **Modalidad:** Chat
- **Resultado:** aceptado → Copilot detectó que ya existía un `pom.xml` en el proyecto, lo leyó primero y lo ajustó en vez de sobreescribirlo desde cero. Quedó con Spring Boot 3.2.2, Java 17, el groupId/artifactId/versión pedidos, las 6 dependencias correctas (incluyendo scopes `runtime` y `test`) y el `spring-boot-maven-plugin`. Validado con `mvn -q validate` sin errores.

---

## Prompt 2 — Modelo Estudiante

- **Prompt:**
  ```
  En package com.academia.batch.model crea: Clase modelo Estudiante con los campos: nombre (String),
  grupo (String), nota1, nota2, nota3 y promedio (todos double). Incluye constructor vacío, getters y
  setters de todos los campos, y un toString que muestre nombre, grupo y promedio.
  ```
- **Modalidad:** Chat
- **Resultado:** corregido → Copilot encontró que ya existía una clase `Estudiante`, pero ubicada en una ruta de paquete duplicada e incorrecta (`.../model/com/academia/batch/model/Estudiante.java`, es decir el paquete anidado dentro de sí mismo). La reubicó al paquete correcto `com.academia.batch.model` y generó ahí los 5 campos, constructor vacío, getters/setters y el `toString()` limitado a nombre, grupo y promedio (tal como se pidió, sin incluir las notas individuales). Validado con `mvn -q test`.

---

## Prompt 3 — EstudianteProcessor (Step 1)

- **Prompt:**
  ```
  Processor de Spring Batch que implementa ItemProcessor<Estudiante, Estudiante>. En el método process:
  calcula el promedio como (nota1 + nota2 + nota3) / 3, asigna el promedio al estudiante con setPromedio,
  registra un log con SLF4J "Step 1 - Procesando: {estudiante}" y devuelve el estudiante.
  ```
- **Modalidad:** Chat
- **Resultado:** corregido → misma situación que con `Estudiante`: existía una clase vacía en una ruta de paquete duplicada. Se movió a `com.academia.batch.processor` y se implementó la lógica pedida. Validado con `mvn -q test`.

---

## Prompt 4 — EstudianteReporte (documento MongoDB)

- **Prompt:**
  ```
  Documento de MongoDB (@Document collection = "reportes_estudiantes") con: id (String, anotado con @Id),
  nombre, grupo, promedio (double) y estado (String). Constructor vacío, getters, setters y toString.
  ```
- **Modalidad:** Chat
- **Resultado:** aceptado → ya existía `EstudianteReporte.java` incompleto; Copilot lo completó con las anotaciones y campos pedidos. Validado con `mvn -q test`.

---

## Prompt 5 — ReporteEstudianteProcessor (umbral de aprobación)

- **Prompt:**
  ```
  Processor que implementa ItemProcessor<Estudiante, EstudianteReporte>. Convierte un Estudiante en un
  EstudianteReporte copiando nombre, grupo y promedio, y asigna estado "APROBADO" si el promedio es >= 70,
  o "REPROBADO" si es menor. Loguea "Step 2 - Reporte: {reporte}" y devuelve el reporte.
  ```
- **Modalidad:** Chat
- **Resultado:** aceptado → Copilot usó la condición `promedio >= 70` exactamente como se pidió; no hizo falta corregir el umbral (verifiqué el operador con la prueba unitaria del caso límite 69.9 vs 70, ver Prompt 12).

---

## Prompt 6 — BatchConfig (Job y Steps)

- **Prompt:**
  ```
  Clase @Configuration de Spring Batch (Spring Boot 3.2) llamada BatchConfig con: Step 1 "paso1"
  (FlatFileItemReader desde estudiantes.csv → EstudianteProcessor → JdbcBatchItemWriter a MySQL),
  Step 2 "paso2" (JdbcCursorItemReader desde estudiantes_procesados → ReporteEstudianteProcessor →
  MongoItemWriter a reportes_estudiantes), ambos con chunk de 3. Job "procesarCalificacionesJob" con
  RunIdIncrementer que ejecuta paso1 y luego paso2. API de Spring Batch 5 (JobBuilder/StepBuilder con
  JobRepository).
  ```
- **Modalidad:** Chat
- **Resultado:** corregido → al generar `BatchConfig`, Copilot también modificó `Main.java` para convertirlo en la clase de arranque (`@SpringBootApplication`), pero el contenido no coincidía con lo esperado; lo revisé y corregí manualmente. Validado con `mvn -q test`.

---

## Prompt 7 — SpringBatchApplication (conflicto real con Main.java)

- **Prompt:**
  ```
  Crea com.academia.batch.SpringBatchApplication: package com.academia.batch; con Clase principal de
  Spring Boot con @SpringBootApplication y el método main que arranca la aplicación con
  SpringApplication.run.
  ```
- **Modalidad:** Chat
- **Resultado:** corregido → Copilot creó `SpringBatchApplication` en el paquete raíz `com.academia.batch` con `@SpringBootApplication` y `SpringApplication.run(...)`, tal como se pidió. Sin embargo, esto generó un conflicto real: ya existía `Main.java` en `com.academia`, que en el Prompt 6 Copilot había convertido en otra clase `@SpringBootApplication`. Tener dos clases con esa anotación en el mismo proyecto es un error, ya que Spring no tiene una única raíz de escaneo de componentes definida. **Corrección:** eliminé manualmente `Main.java`, dejando `SpringBatchApplication` como única clase de arranque, alineado con la convención estándar (la clase de arranque debe nombrarse según el proyecto y vivir en el paquete raíz, no llamarse genéricamente `Main`). Validado con `mvn -q test`.

---

## Prompt 8 — application.properties (conexión MySQL/Mongo)

- **Prompt:**
  ```
  Genera un application.properties para Spring Boot que se conecte a MySQL en
  jdbc:mysql://localhost:3306/academia (usuario alumno, password alumno123), inicialice el esquema de
  Spring Batch siempre, ejecute el Job al arrancar, y se conecte a MongoDB en
  mongodb://root:root123@localhost:27018/academia?authSource=admin.
  ```
- **Modalidad:** Chat
- **Resultado:** corregido / verificado → el archivo existente tenía el nombre mal escrito (`aplication.properties`), lo renombré a `application.properties`. El puerto de MongoDB (`27018`) coincidió correctamente con lo pedido — Copilot no lo inventó ni lo cambió a su valor por defecto (`27017`), sino que respetó el dato exacto dado en el prompt. **Verificación manual:** confirmé que `27018` es el puerto real de mi contenedor MongoDB local, un dato de mi entorno que Copilot no podía adivinar por sí mismo. Validado con `mvn -q test`.

---

## Prompt 9 — EstudianteEntity, repositorios y corrección de un error propio

- **Prompts:**
  1. `Entidad JPA (@Entity, @Table name="estudiantes_procesados") con id Long (@Id, @GeneratedValue IDENTITY), campos nombre, grupo, nota1, nota2, nota3, promedio; getters y setters.`
  2. `EstudianteRepository extiende JpaRepository<EstudianteEntity, Long> con findByGrupo(String grupo). ReporteRepository extiende MongoRepository<EstudianteReporte, String> con findByEstado(String estado).`
  3. `y borra la clase Estudiante entity` → seguido de `no, vuelve a regresarlos`
- **Modalidad:** Chat
- **Resultado:** corregido → en el primer intento Copilot creó una clase llamada `EstudianteProcesado` en vez de `EstudianteEntity`; se corrigió pidiendo explícitamente el nombre exacto. Luego, por error propio, pedí borrar `EstudianteEntity` (lo cual también eliminó `EstudianteRepository`, que dependía de ella); al notar el error pedí revertirlo, y además tuve que pedir por separado que restaurara `ReporteRepository`, que se había quedado fuera de la restauración. Validado con `mvn -q test` en cada paso.

---

## Prompt 10 — EstudianteService

- **Prompt:**
  ```
  En EstudianteService haz @Service con inyección por constructor de EstudianteRepository. Método
  contarAprobados() que devuelve cuántos estudiantes tienen promedio >= 70, usando findAll() y un
  stream con filter y count.
  ```
- **Modalidad:** Chat
- **Resultado:** aceptado → se generó el servicio con constructor e implementación exacta pedida. Validado con `mvn -q test`.

---

## Prompt 11 — EstudianteController y ReporteController

- **Prompts:**
  1. `@RestController en /api/estudiantes que use EstudianteRepository y EstudianteService (inyectados por constructor) con: GET / (listar), GET /{id} (200/404), GET /aprobados/total (Map con conteo), POST / (201), PUT /{id} (200/404), PATCH /{id} (cambia grupo, 200/404), DELETE /{id} (204/404). Usa ResponseEntity.`
  2. `@RestController en /api/reportes que usa ReporteRepository: GET / lista todos los reportes; GET /estado/{estado} devuelve los que tengan ese estado (convertido a mayúsculas) usando findByEstado.`
- **Modalidad:** Chat
- **Resultado:** aceptado → ambos controladores se generaron con los endpoints y códigos de estado exactos pedidos, sin necesidad de corrección. Validado con `mvn -q test`.

---

## Prompt 12 — Tests unitarios de los Processors

- **Prompt:**
  ```
  Genera pruebas unitarias con JUnit 5 para EstudianteProcessor y ReporteEstudianteProcessor: que
  verifiquen que el promedio se calcula bien, y que el estado es APROBADO con promedio 70 y REPROBADO
  con 69.9.
  ```
- **Modalidad:** Chat
- **Resultado:** aceptado → se generaron ambas clases de test, cubriendo el cálculo del promedio y el caso límite exacto del umbral (70 vs 69.9). Validado con `mvn -q test`.

---

## Prompt 13 — Test de EstudianteService con Mockito

- **Prompt:**
  ```
  Prueba unitaria de EstudianteService con Mockito: mockea EstudianteRepository con @Mock, inyecta el
  servicio con @InjectMocks, usa @ExtendWith(MockitoExtension.class), simula findAll() devolviendo
  2 estudiantes aprobados y 1 reprobado, y verifica que contarAprobados() devuelve 2.
  ```
- **Modalidad:** Chat
- **Resultado:** aceptado → se generó el test exactamente como se pidió, sin correcciones. Validado con `mvn -q test`.

---

## Prompt 14 — Prompt malo vs. bueno (Bloque 3)

- **Malo:** `haz un metodo de reprobados`
- **Bueno (el que realmente usé, Prompt 10):**
  ```
  En EstudianteService haz @Service con inyección por constructor de EstudianteRepository. Método
  contarAprobados() que devuelve cuántos estudiantes tienen promedio >= 70, usando findAll() y un
  stream con filter y count.
  ```
- **Diferencia observada:** el prompt "malo" no dice el umbral de aprobación, el patrón de inyección ni cómo implementar la lógica (stream/filter/count), por lo que Copilot tendría que inventar esas decisiones. El prompt "bueno" fija el criterio exacto (`>= 70`), la técnica pedida y el estilo de inyección, y por eso se obtuvo el método correcto al primer intento, sin corrección posterior.

---

## Prompt 15 — Refactor sin romper (Javadoc)

- **Prompt:**
  ```
  Agrega comentarios Javadoc a EstudianteService.java, documentando la clase y cada método público:
  su propósito, parámetros y valor de retorno.
  ```
- **Modalidad:** Chat
- **¿Tests siguieron en verde?:** sí → corrí `mvn -q test` después del cambio y no se rompió nada. Como extra, Copilot detectó y eliminó un `import` que había quedado sin uso tras el refactor.

---

## Anexo — Comparación adicional de BatchConfig (Copilot vs. mi implementación)

*(No corresponde a un prompt nuevo; lo dejo como evidencia complementaria del refactor del Prompt 15.)*

Comparé el `BatchConfig` generado por Copilot contra mi propia revisión del mismo archivo y corregí dos decisiones:

**1. TransactionManager innecesariamente explícito**

Copilot definió manualmente:
```java
@Bean
public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
}
```
Lo corregí usando `PlatformTransactionManager` (interfaz) como tipo de parámetro en los steps y eliminando el bean manual, ya que Spring Boot lo autoconfigura al detectar un `DataSource`.

**2. RowMapper automático riesgoso**

Copilot usó:
```java
.rowMapper(new BeanPropertyRowMapper<>(Estudiante.class))
```
Lo corregí con un `RowMapper` lambda explícito que solo setea `nombre`, `grupo` y `promedio` (los campos que realmente trae el SELECT), evitando el mapeo automático por reflexión sobre un objeto con más campos de los que trae el query.

---
- **Carpeta `target/`:** es la salida de compilación de Maven; debe estar en `.gitignore`.