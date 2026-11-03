# Employee Salary Management System — Architecture & Design Notes

## 1. Overview

The Employee Salary Management System is a web-based application designed for an HR Manager to manage employee information and salary records for an organization of approximately 10,000 employees.

The system replaces spreadsheet-based salary management with a centralized application providing:

- Employee management
- Salary management and salary history
- Search, filtering, sorting and pagination
- Salary statistics
- Country-wise employee distribution
- Input validation and centralized error handling

## 2. High-Level Architecture

```text
                    ┌──────────────────────────┐
                    │       HR Manager         │
                    │      Web Browser         │
                    └────────────┬─────────────┘
                                 │
                                 │ HTTP / REST
                                 ▼
                    ┌──────────────────────────┐
                    │     Angular Frontend     │
                    │                          │
                    │ Dashboard                │
                    │ Employee Management     │
                    │ Salary Management        │
                    └────────────┬─────────────┘
                                 │
                                 │ REST API
                                 ▼
              ┌────────────────────────────────────┐
              │          Spring Boot Backend       │
              │                                    │
              │ Controllers                         │
              │      ↓                             │
              │ Services                            │
              │      ↓                             │
              │ Repositories                        │
              │      ↓                             │
              │ DTOs / Mappers / Validation         │
              └────────────────┬───────────────────┘
                               │
                               │ JDBC / JPA
                               ▼
                    ┌──────────────────────────┐
                    │        MySQL 8           │
                    │                          │
                    │ employees                │
                    │ employee_salaries        │
                    └──────────────────────────┘
```

## 3. Frontend Architecture

The frontend is implemented using Angular.

### Main UI areas

```text
Angular Application
│
├── Dashboard
│   ├── Total Employees
│   ├── Total Countries
│   ├── Employees With Salary
│   ├── Country Distribution
│   └── Salary Statistics
│
├── Employee Management
│   ├── Employee List
│   ├── Search
│   ├── Country Filter
│   ├── Sorting
│   ├── Pagination
│   ├── Create Employee
│   ├── Edit Employee
│   └── Delete Employee
│
└── Salary Management
    ├── Employee Selection
    ├── Salary History
    ├── Add Salary
    ├── Edit Salary
    └── Delete Salary
```

The frontend communicates with the backend through REST APIs using Angular's `HttpClient`.

Server-side pagination, filtering and sorting are used for employee data so that the frontend does not need to load all 10,000 employees at once.

## 4. Backend Architecture

The backend follows a layered architecture.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Controller Layer

Responsible for:

- Receiving HTTP requests
- Request validation
- Returning HTTP responses
- Mapping API endpoints to application operations

Main controllers:

- `EmployeeController`
- `SalaryController`
- `DashboardController`

### Service Layer

Contains business logic.

Main services:

- `EmployeeService`
- `SalaryService`
- `DashboardService`

Examples of business logic include:

- Employee creation and updates
- Salary validation
- Salary history retrieval
- Total compensation calculation
- Dashboard statistics

### Repository Layer

Uses Spring Data JPA repositories to communicate with MySQL.

Main repositories:

- `EmployeeRepository`
- `EmployeeSalaryRepository`

Database-specific aggregation is used for dashboard statistics where appropriate.

### DTO Layer

DTOs are used to separate API request/response models from database entities.

Examples:

- `EmployeeCreateRequest`
- `EmployeeUpdateRequest`
- `SalaryCreateRequest`
- `SalaryUpdateRequest`
- `EmployeeResponse`
- `SalaryResponse`
- `ApiResponse`

This avoids exposing persistence entities directly as the API contract.

### Mapper Layer

Mapper classes convert between entities and DTOs.

- `EmployeeMapper`
- `SalaryMapper`

## 5. Database Design

The core data model contains two primary tables.

```text
┌──────────────────────────┐
│        employees         │
├──────────────────────────┤
│ id (PK)                  │
│ employee_code (UNIQUE)   │
│ first_name               │
│ last_name                │
│ email (UNIQUE)           │
│ country                  │
│ created_at               │
│ updated_at               │
└─────────────┬────────────┘
              │
              │ 1 : N
              │
┌─────────────▼────────────┐
│     employee_salaries    │
├──────────────────────────┤
│ id (PK)                  │
│ employee_id (FK)         │
│ base_salary              │
│ bonus                    │
│ currency                 │
│ effective_date           │
│ created_at               │
│ updated_at               │
└──────────────────────────┘
```

### Relationship

One employee can have multiple salary records.

This supports salary history instead of overwriting previous salary information.

```text
Employee 1 ─────────── N Salary Records
```

## 6. Salary Calculation

Total compensation is calculated as:

```text
Total Compensation = Base Salary + Bonus
```

No currency conversion is performed.

For example:

```text
Base Salary: 50,000 INR
Bonus:        5,000 INR
Total:       55,000 INR
```

A USD salary remains in USD and is not converted to INR.

This keeps salary records explicit and avoids introducing foreign-exchange dependencies that are outside the assessment scope.

## 7. API Design

### Employee APIs

```text
GET    /api/employees
GET    /api/employees/{id}
POST   /api/employees
PUT    /api/employees/{id}
DELETE /api/employees/{id}

GET    /api/employees/search
```

The search endpoint supports:

- Search text
- Country filtering
- Pagination
- Sorting
- Sort direction

### Salary APIs

```text
GET    /api/employees/{employeeId}/salaries
POST   /api/employees/{employeeId}/salaries

PUT    /api/salaries/{salaryId}
DELETE /api/salaries/{salaryId}
```

### Dashboard APIs

```text
GET /api/dashboard/summary
GET /api/dashboard/country-distribution
GET /api/dashboard/salary-statistics
```

## 8. Validation and Error Handling

Request validation is implemented using Jakarta Bean Validation.

Examples include:

- Required employee fields
- Email format
- Salary must not be negative
- Currency must be a three-letter uppercase code
- Effective date is required

A centralized `GlobalExceptionHandler` provides consistent API error responses.

Handled cases include:

```text
400 Bad Request
    → Validation errors

404 Not Found
    → Requested resource does not exist

409 Conflict
    → Duplicate employee code/email

500 Internal Server Error
    → Unexpected server errors
```

## 9. Performance Considerations

The application was tested with approximately 10,000 employee records.

### Server-side pagination

Employee records are paginated on the backend rather than loading all records into the browser.

This reduces:

- API response size
- Browser memory usage
- Rendering workload

### Database indexing

Frequently queried fields such as employee code, email and country use appropriate database constraints/indexing.

### Aggregation

Dashboard statistics are calculated using database queries rather than transferring all employee records to the frontend.

### Performance verification

The application was tested against the 10,000-employee dataset.

The salary history API was observed with approximately:

```text
Time to first byte: ~12 ms
Total request time: ~17 ms
```

Other tested API requests generally completed within a low-millisecond range during local testing.

These measurements are local development measurements and are not intended to represent production infrastructure performance.

## 10. Testing Strategy

The backend contains unit tests using JUnit and Mockito.

Tests cover important service-level behavior including:

- Employee service operations
- Salary service operations
- Dashboard service behavior
- Salary validation
- Salary calculations
- Error scenarios

The final backend test run completed successfully with:

```text
33 tests
0 failures
0 errors
0 skipped
```

## 11. Design Decisions and Trade-offs

### MySQL

A relational database was selected because employee and salary information has structured relationships and requires transactional consistency.

### Salary history

Salary records are stored separately from employees to support multiple salary records per employee and preserve historical information.

### Server-side pagination

Server-side pagination was selected because the system is expected to support approximately 10,000 employees and potentially larger datasets in the future.

### No currency conversion

Foreign exchange conversion was deliberately excluded because the assessment requires salary management rather than financial exchange-rate processing.

### No authentication/authorization

Authentication and authorization are outside the implemented assessment scope. The application focuses on the HR salary management workflow.

### Layered backend

Controllers, services and repositories are separated to improve maintainability, testing and separation of concerns.

### Centralized error handling

A global exception handler provides consistent API responses and avoids duplicating error-handling logic across controllers.

## 12. Deliberately Out of Scope

The following capabilities are not implemented:

- Payroll processing
- Tax calculation
- Foreign exchange conversion
- Attendance management
- Leave management
- Benefits management
- Recruitment
- Performance management
- Payslip generation
- External HR integrations
- Mobile application
- Advanced salary forecasting

These features can be added later without changing the core employee/salary data model significantly.

## 13. Scalability Considerations

The current design can be extended by:

- Adding database indexes as query patterns grow
- Introducing caching for frequently requested dashboard information
- Separating services into independently deployable services if scale requires it
- Adding authentication and role-based authorization
- Introducing asynchronous processing for heavy reporting operations
- Adding production monitoring and centralized logging

For the assessment scope, a modular monolithic Spring Boot application provides a simpler implementation while maintaining clear separation between layers.

## 14. Conclusion

The architecture focuses on simplicity, maintainability and correctness while supporting the required employee and salary management workflows.

The system has been verified with approximately 10,000 employee records and provides:

- Employee CRUD
- Salary CRUD and history
- Search/filter/sort/pagination
- Dashboard statistics
- Validation
- Centralized error handling
- Automated backend tests
- Multi-currency salary records without currency conversion