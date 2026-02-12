# Fintech Wallet Backend (Spring Boot) — DEV

Backend API for a fintech-style e-wallet application built with **Spring Boot** and **PostgreSQL**.

> ⚠️ This repository is currently under active development on the `dev` branch.  
> Features and endpoints may change.

---

## Tech Stack
- Java (Spring Boot)
- Spring Security + JWT
- PostgreSQL
- Maven

---

## Project Goals
This project is designed as a fintech-style wallet backend with:
- ACID-safe transactions
- Double-entry ledger approach (planned / in progress)
- Audit-friendly transaction records (planned / in progress)

---

## Current Features (DEV)
- [x] User registration & login
- [x] JWT authentication
- [x] Get current user profile (`/user/me`)
- [x] Wallet balance endpoint
- [x] Top-up wallet (secured using JWT userId)
- [x] Transfer wallet-to-wallet (basic)

---

## Planned Features
- [ ] Transaction history (ledger-based)
- [ ] Full double-entry ledger implementation
- [ ] Audit logs
- [ ] Swagger / OpenAPI documentation
- [ ] Docker compose (backend + PostgreSQL)
- [ ] Admin tools / monitoring

---

## Run Locally

### 1) Clone repository
```bash
git clone https://github.com/Bapakardhie93/fintech-wallet-backend.git
cd fintech-wallet-backend
git checkout dev
```

### 2) Setup PostgreSQL
Create a PostgreSQL database manually (example):
- Database: `fintech_wallet`
- Username: `postgres`
- Password: `postgres`

### 3) Configure application properties
Update your database config inside:
- `src/main/resources/application.properties` (or `application.yml`)

Example:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fintech_wallet
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### 4) Run the project
```bash
./mvnw spring-boot:run
```

Backend will run on:
```txt
http://localhost:8080
```

---

## Frontend
This repository contains **backend only**.

Frontend projects are developed separately:
- Web frontend (React)
- iOS app (SwiftUI)

---

## Branching
- `main` → stable / release-ready
- `dev` → active development (current work)

---

## License
This project is for learning and portfolio purposes.
