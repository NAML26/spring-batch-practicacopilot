# spring-batch-final-calificaciones

Proyecto Spring Boot 3.2.2 para gestionar estudiantes, calcular promedios y exponer reportes en MySQL y MongoDB.

## Tecnologias

- Java 17
- Spring Boot
- Spring Batch
- Spring Data JPA + MySQL
- Spring Data MongoDB
- Spring Web
- JUnit 5 + Mockito

## Que hace

- Lee estudiantes con notas.
- Calcula el promedio.
- Marca el estado del reporte como `APROBADO` o `REPROBADO` segun el promedio.
- Expone endpoints REST para estudiantes y reportes.

## Estructura principal

- `src/main/java/com/academia/Main.java`: clase de arranque de la aplicacion.
- `src/main/java/com/academia/batch/model/Estudiante.java`: modelo base de estudiante.
- `src/main/java/com/academia/batch/repository/EstudianteEntity.java`: entidad JPA para estudiantes procesados.
- `src/main/java/com/academia/batch/model/EstudianteReporte.java`: documento MongoDB para reportes.
- `src/main/java/com/academia/batch/processor/EstudianteProcessor.java`: calcula el promedio.
- `src/main/java/com/academia/batch/processor/ReporteEstudianteProcessor.java`: genera el reporte con estado.
- `src/main/java/com/academia/batch/controller/EstudianteController.java`: API de estudiantes.
- `src/main/java/com/academia/batch/controller/ReporteController.java`: API de reportes.

## Requisitos

- Java 17
- Maven
- MySQL local
- MongoDB local

## Configuracion

El archivo `src/main/resources/application.properties` usa estos valores por defecto:

- MySQL: `jdbc:mysql://localhost:3306/academia`
- Usuario: `alumno`
- Password: `alumno123`
- MongoDB: `mongodb://root:root123@localhost:27018/academia?authSource=admin`

## Ejecutar

```bash
mvn spring-boot:run
```

## Probar la API

### Estudiantes

- `GET /api/estudiantes`
- `GET /api/estudiantes/{id}`
- `GET /api/estudiantes/aprobados/total`
- `POST /api/estudiantes`
- `PUT /api/estudiantes/{id}`
- `PATCH /api/estudiantes/{id}`
- `DELETE /api/estudiantes/{id}`

### Reportes

- `GET /api/reportes`
- `GET /api/reportes/estado/{estado}`

## CSV de entrada

El archivo `src/main/resources/estudiantes.csv` contiene datos de ejemplo con este formato:

```csv
nombre,grupo,nota1,nota2,nota3
```

## Pruebas

```bash
mvn test
```
