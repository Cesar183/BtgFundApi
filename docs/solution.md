# BTG Fund - Solucion Tecnica

## 1. Arquitectura y stack
- Lenguaje: Java 21
- Framework: Spring Boot
- Base de datos NoSQL: MongoDB
- Seguridad: Spring Security + JWT (stateless)
- Notificaciones: proveedor por estrategia (EMAIL/SMS)

## 2. Modelo de datos NoSQL (MongoDB)

### Coleccion `funds`
```json
{
  "_id": "1",
  "name": "FPV_BTG_PACTUAL_RECAUDADORA",
  "minimumAmount": 75000,
  "category": "FPV"
}
```

### Coleccion `users`
```json
{
  "_id": "<objectId>",
  "email": "cliente@correo.com",
  "fullName": "Nombre Cliente",
  "phone": "3001234567",
  "passwordHash": "$2a$10$...",
  "preferredNotificationChannel": "EMAIL",
  "availableBalance": 500000,
  "roles": ["ROLE_USER"],
  "createdAt": "2026-03-12T00:00:00Z"
}
```

### Coleccion `subscriptions`
```json
{
  "_id": "<objectId>",
  "userId": "<userId>",
  "fundId": "1",
  "fundName": "FPV_BTG_PACTUAL_RECAUDADORA",
  "linkedAmount": 75000,
  "status": "ACTIVE",
  "createdAt": "2026-03-12T00:00:00Z",
  "canceledAt": null
}
```

### Coleccion `transactions`
```json
{
  "_id": "uuid-v4",
  "userId": "<userId>",
  "fundId": "1",
  "fundName": "FPV_BTG_PACTUAL_RECAUDADORA",
  "type": "OPEN",
  "amount": 75000,
  "createdAt": "2026-03-12T00:00:00Z",
  "notificationChannel": "EMAIL"
}
```

## 3. Endpoints REST
- `POST /api/auth/register`: registro de cliente con saldo inicial COP 500000
- `POST /api/auth/login`: autenticacion y entrega de JWT
- `GET /api/funds`: lista fondos disponibles
- `POST /api/subscriptions/{fundId}`: apertura de fondo
- `DELETE /api/subscriptions/{subscriptionId}`: cancelacion y devolucion de saldo
- `GET /api/subscriptions/active`: fondos activos del cliente
- `GET /api/transactions`: historial del cliente
- `GET /api/me/balance`: saldo disponible del cliente
- `GET /api/admin/users/{userId}/transactions`: historial por usuario (solo ADMIN)

## 4. Reglas de negocio implementadas
- Saldo inicial por usuario: `500000`
- Cada transaccion usa identificador unico `UUID`
- Validacion por monto minimo de cada fondo
- Mensaje exacto por saldo insuficiente:
  - `No tiene saldo disponible para vincularse al fondo <Nombre del fondo>`
- Al cancelar suscripcion se retorna el valor vinculado al saldo del cliente

## 5. Seguridad
- Autenticacion: JWT firmado HMAC-SHA
- Autorizacion: roles `ROLE_USER`, `ROLE_ADMIN`
- Perfilamiento:
  - USER: sus operaciones
  - ADMIN: consulta cross-user (`/api/admin/...`)
- Encriptacion:
  - Password hash con `BCrypt`
  - JWT firmado con secreto configurable (`JWT_SECRET`)

## 6. Manejo de excepciones
- `@RestControllerAdvice` centralizado
- Errores de validacion, negocio, autenticacion y no encontrados
- Respuesta uniforme con `timestamp`, `status`, `error`, `message`

## 7. Calidad
- Clean Code por capas: `api`, `service`, `repository`, `domain`, `security`
- Pruebas unitarias para casos criticos de autenticacion y suscripciones

