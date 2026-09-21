# GuildBoard

GuildBoard is a full-stack web application designed for managing guild adventurers and quest assignments.

---

## Project Structure

```text
GuildBoard/
├── backend/     # Spring Boot REST API
└── frontend/    # React (TypeScript + Vite) application
```

## Prerequisites

- Java 17 or higher
- Node.js (v18+) and npm
- PostgreSQL running locally on port `5432` with a database named `guildboard`

## Getting Started

### 1. Database Setup

Ensure PostgreSQL is running:

- Port: `5432`
- Database: `guildboard`
- Configure your credentials in `backend/src/main/resources/application.properties` if needed.

### 2. Backend (Spring Boot)

Navigate to the backend directory and start the server:

```bash
cd backend
./mvnw spring-boot:run
```

- API Base URL: `http://localhost:8080/api`
- Swagger UI Documentation: `http://localhost:8080/swagger-ui/index.html`

### 3. Frontend (React + Vite)

In a separate terminal, navigate to the frontend directory:

```bash
cd frontend
npm install
npm run dev
```

- Local Web App: `http://localhost:5173`

## Key Features

- **Adventurers Management**: View, create, update, delete adventurers, and track level/XP progress.
- **Quest Board**: List quests with filters by status and difficulty.
- **Assignment System**: Assign available quests to eligible adventurers and mark quests as completed.
- **Strict Typing**: Frontend built with strict TypeScript types (zero use of `any`).

## Tech Stack

- **Backend**: Java, Spring Boot, Spring Data JPA, PostgreSQL, Springdoc OpenAPI (Swagger).
- **Frontend**: React, TypeScript, Vite, CSS Modules.