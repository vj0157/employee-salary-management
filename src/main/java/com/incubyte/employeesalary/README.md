# Employee Salary Management System

A full-stack web application for managing employee information and salary records for a global organization.

The system allows HR managers to manage employees, maintain salary history, view compensation information, and analyze employee and salary data across multiple countries and currencies.

---

## Features

### Employee Management
- Create employees
- View employee details
- Edit employee information
- Delete employees
- Search employees
- Filter employees by country
- Server-side pagination
- Server-side sorting
- Employee code and email uniqueness validation

### Salary Management
- Assign salary records to employees
- Maintain salary history
- Update salary records
- Delete salary records
- Support multiple currencies
- Calculate total compensation automatically

**Total Compensation = Base Salary + Bonus**

### Dashboard
- Total employee count
- Total countries
- Employees with salary records
- Employee distribution by country
- Salary statistics grouped by currency
- Average, minimum and maximum salary information

### Validation & Error Handling
The application handles:
- Required field validation
- Email validation
- Duplicate employee code/email
- Non-existent employee records
- Invalid salary values
- Invalid salary currency
- Invalid salary dates
- Unexpected server errors

---

## Technology Stack

### Frontend

- Angular
- TypeScript
- Tailwind CSS
- RxJS
- Angular Router
- Angular HttpClient

### Backend

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- REST APIs

### Database

- MySQL 8
- Database: `employee_salary_db`

### Testing

- JUnit
- Spring Boot Test
- xUnit

---

## Project Structure

```text
employee-salary-management/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/incubyte/employeesalary/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

Frontend:

```text
employee-salary-management-ui/
│
├── src/
│   └── app/
│       ├── features/
│       │   ├── dashboard/
│       │   ├── employees/
│       │   └── salaries/
│       │
│       ├── services/
│       ├── app.ts
│       ├── app.html
│       ├── app.config.ts
│       └── app.routes.ts
│
├── package.json
└── angular.json
```

---

# Architecture

The application follows a standard three-layer architecture.

```text
Angular Frontend
       |
       | REST API
       |
Spring Boot Backend
       |
       | JPA / Hibernate
       |
MySQL Database
```

### Backend Layers

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

The backend also uses DTOs for API requests and responses and provides centralized exception handling through a global exception handler.

---

# Database

The application uses MySQL.

Create the database:

```sql
CREATE DATABASE employee_salary_db;
```

Then configure the database connection in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_salary_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

Replace `YOUR_PASSWORD` with the local MySQL password.

---

# Backend Setup

## Prerequisites

Install:

- Java 17
- MySQL 8
- Git

Maven does not need to be installed globally because the project includes the Maven Wrapper.

Verify Java:

```bash
java -version
```

---

## Start MySQL

Make sure the MySQL server is running.

Create the database:

```sql
CREATE DATABASE employee_salary_db;
```

Update the database credentials in:

```text
src/main/resources/application.properties
```

---

## Run Backend

Open a terminal in:

```text
employee-salary-management
```

### Windows

```cmd
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

The backend runs on:

```text
http://localhost:8080
```

---

# Frontend Setup

## Prerequisites

Install:

- Node.js
- npm
- Angular CLI

Verify:

```bash
node -v
npm -v
ng version
```

Navigate to the frontend project:

```text
employee-salary-management-ui
```

Install dependencies:

```bash
npm install
```

Start the Angular application:

```bash
ng serve
```

The frontend runs on:

```text
http://localhost:4200
```

---

# API Endpoints

## Employee APIs

### Get Employees

```http
GET /api/employees
```

### Get Employee

```http
GET /api/employees/{id}
```

### Create Employee

```http
POST /api/employees
```

### Update Employee

```http
PUT /api/employees/{id}
```

### Delete Employee

```http
DELETE /api/employees/{id}
```

### Search Employees

```http
GET /api/employees/search
```

Supported parameters include:

```text
search
country
page
size
sortBy
direction
```

Example:

```text
/api/employees/search?search=John&country=India&page=0&size=10&sortBy=firstName&direction=asc
```

---

# Salary APIs

### Get Employee Salary History

```http
GET /api/employees/{employeeId}/salaries
```

### Create Salary

```http
POST /api/employees/{employeeId}/salaries
```

### Update Salary

```http
PUT /api/salaries/{salaryId}
```

### Delete Salary

```http
DELETE /api/salaries/{salaryId}
```

---

# Dashboard APIs

### Dashboard Summary

```http
GET /api/dashboard/summary
```

### Country Distribution

```http
GET /api/dashboard/country-distribution
```

### Salary Statistics

```http
GET /api/dashboard/salary-statistics
```

---

# Salary Calculation

Salary compensation is calculated automatically by the backend.

```text
Total Compensation = Base Salary + Bonus
```

Example:

```text
Base Salary = 50,000
Bonus       = 5,000

Total Compensation = 55,000
```

The system supports multiple currencies.

Examples:

```text
INR
USD
GBP
CAD
AUD
```

Salary amounts are maintained in their respective currencies. The application does not perform foreign exchange conversion.

---

# Pagination and Performance

Employee search uses server-side pagination to avoid loading all employee records into the browser.

The API supports:

```text
page
size
sortBy
direction
```

The application was tested with **10,000 employee records**.

The following operations were verified with the 10,000-record dataset:

- Employee listing
- Pagination
- Search
- Country filtering
- Sorting
- Dashboard statistics

The database also uses appropriate indexing for frequently queried employee fields.

---

# Validation and Error Handling

The backend uses centralized exception handling.

Common HTTP responses include:

### 400 Bad Request

Used for invalid input such as:

- Missing required fields
- Invalid email
- Negative salary
- Invalid currency
- Invalid date

### 404 Not Found

Returned when the requested employee or salary record does not exist.

### 409 Conflict

Returned when a unique employee field such as employee code or email already exists.

### 500 Internal Server Error

Returned for unexpected server-side errors.

API responses follow a common structure:

```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": {},
  "timestamp": "..."
}
```

---

# Testing

Run the complete backend test suite using:

### Windows

```cmd
.\mvnw.cmd clean test
```

### Linux / macOS

```bash
./mvnw clean test
```

The project test suite covers controller/service functionality, validation, exception handling and other backend behavior.

---

# Running the Complete Application

Start MySQL first.

Then start the backend:

```cmd
.\mvnw.cmd spring-boot:run
```

Start the frontend in a separate terminal:

```bash
ng serve
```

Open:

```text
http://localhost:4200
```

---

# Main Application Screens

The application contains:

### Dashboard

Provides:

- Employee count
- Country count
- Employees with salary
- Country distribution
- Salary statistics

### Employees

Provides:

- Employee list
- Search
- Country filtering
- Pagination
- Sorting
- Add employee
- Edit employee
- Delete employee
- Employee salary information

### Salary Management

Provides:

- Employee selection
- Salary history
- Add salary
- Edit salary
- Delete salary
- Multiple currencies
- Total compensation calculation

---

# Performance Verification

A dataset of 10,000 employees was created to validate application behavior at a larger data volume.

The application successfully handled:

```text
10,000 employees
```

with:

- Server-side pagination
- Server-side search
- Country filtering
- Sorting
- Dashboard aggregation

This prevents the frontend from loading all employee records simultaneously.

---

# Project Status

The following functionality has been implemented and verified:

- [x] Employee CRUD
- [x] Employee search
- [x] Country filtering
- [x] Pagination
- [x] Sorting
- [x] Salary CRUD
- [x] Salary history
- [x] Salary calculation
- [x] Multi-currency support
- [x] Dashboard
- [x] Validation
- [x] Centralized error handling
- [x] 10,000 employee performance verification
- [x] Backend automated tests
- [x] Frontend integration

---

# Local Development

Backend:

```text
http://localhost:8080
```

Frontend:

```text
http://localhost:4200
```

Database:

```text
employee_salary_db
```

---

# Notes

This project is intended as a full-stack employee and salary management application for local development and evaluation.

The application is designed with separation between frontend, backend and database layers to make the system easier to maintain and extend.