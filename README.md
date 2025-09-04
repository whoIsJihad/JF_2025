# travL: Smart Itinerary Planner for Business Travelers

This project is a **Spring Boot** web application designed to help business travelers make the most of their free time. It provides personalized recommendations for attractions, restaurants, and cultural tips based on the user's schedule, preferences, and location.

---

## Technology Stack

This is a backend-focused project built on a modern Java stack. The core technologies are:

- **Backend Framework:** [Spring Boot](https://spring.io/projects/spring-boot)
- **Language:** Java 17
- **Database:** [PostgreSQL](https://www.postgresql.org/)
- **Data Access:** Spring Data JPA (Hibernate)
- **Build Tool:** [Apache Maven](https://maven.apache.org/)
- **Security:** Spring Security (for password hashing)

---

## Prerequisites

Before you begin, ensure you have the following installed:

- JDK 17 or later  
- Apache Maven 3.6+  
- PostgreSQL 12+  

---

## Setup

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd <your-project-directory>
```

### 2. Create a PostgreSQL Database

You can use `psql` or pgAdmin:

```sql
CREATE DATABASE myapp_db;
```

### 3. Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/myapp_db
spring.datasource.username=<your_postgres_username>
spring.datasource.password=<your_postgres_password>
```

---

## Running the Application

### 1. Build and Run

From the project root:

```bash
mvn spring-boot:run
```

This will start the server on `http://localhost:8080`.

The first run will auto-create the DB schema using Hibernate (`ddl-auto=update`).

### 2. (Optional) Seed the Database

To test the APIs, load initial data using the provided `data.sql` script.

---

## API Endpoints

### User Management

- `POST /api/users/register` — Register a new user

### Trip Management

- `POST /api/trips` — Create a new trip with meeting slots and preferences

### Itinerary Planning

- `GET /api/itineraries/{tripId}/free-slots` — Calculate available free time slots
- `GET /api/itineraries/{tripId}/attractions` — Get personalized attraction recommendations

---
