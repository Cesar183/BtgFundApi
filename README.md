# BTG Fund API

Backend en Spring Boot para gestion de fondos de inversion (apertura, cancelacion, historial y notificaciones).

## Requisitos
- Java 21
- Gradle (se incluye wrapper `./gradlew`)
- MongoDB (local o Atlas)

## Ejecucion local (rapido)
1. Levantar MongoDB local:
```bash
docker run -d --name btg-mongo -p 27017:27017 mongo:7
```
2. (Opcional) Exportar variables:
```bash
export MONGODB_URI=mongodb://localhost:27017/BTG
export JWT_SECRET=U3VwZXJTZWNyZXRLZXlGb3JCdGdGdW5kQXBwMTIzNDU2Nzg5MA==
export JWT_EXP_MINUTES=120
```
3. Ejecutar pruebas:
```bash
./gradlew clean test
```
4. Iniciar API:
```bash
./gradlew bootRun
```
5. Verificar estado:
```bash
curl http://localhost:8080/actuator/health
```

## Swagger
- UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Endpoints protegidos requieren token JWT (Authorize -> `Bearer <token>`)

## Probar flujo con Postman
Coleccion incluida:
- `docs/postman/BTG-Fund-Flow.postman_collection.json`

Flujo cubierto:
- `register -> login -> subscribe -> cancel -> history`

Configura `baseUrl = http://localhost:8080` y ejecutar la coleccion en orden.

## SQL (Parte 2)
Scripts incluidos:
- Esquema: `sql/schema_parte2.sql`
- Consulta: `sql/consulta_parte2.sql`
