# Quiz Management System - Backend API

Production-grade RESTful backend service built with Spring Boot, Spring Security (JWT + HttpOnly Refresh Token Cookies), Redis, and MySQL.

## Tech Stack
- **Framework:** Java 17+, Spring Boot 3, Spring Security, Spring Data JPA
- **Database & Cache:** MySQL 8, Redis
- **Containerization:** Docker

## Environment Variables
Create environment variables in your deployment platform (Render/Railway):
- `DB_URL`: JDBC MySQL Connection URL
- `DB_USER`: Database username
- `DB_PASS`: Database password
- `JWT_SECRET`: Strong 256-bit secret key
- `FRONTEND_URL`: Deployed frontend origin for CORS
- `REDIS_HOST` / `REDIS_PORT` / `REDIS_PASSWORD`: Redis configuration

## Health Check
- `GET /api/v1/health` — Returns service uptime and status.