# Employee Salary Management System — Design Decisions & Trade-offs

## 1. Technology Choices

### Spring Boot

Java with Spring Boot was selected for the backend because the assessment specifically prefers Java and the framework provides a mature structure for REST APIs, validation, dependency injection, database access and testing.

### Angular

Angular was selected for the frontend to provide a structured component-based application for the HR Manager workflow.

### MySQL

MySQL was selected because employee and salary information is relational and requires:

- Primary and foreign keys
- Unique constraints
- Structured relationships
- Reliable persistence
- Aggregation queries
- Transactional consistency

---

## 2. Layered Backend Architecture

The backend uses:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Why?

This separation keeps responsibilities clear.

Controllers handle HTTP requests, services contain business logic, and repositories handle persistence.

Benefits include:

- Easier testing
- Easier maintenance
- Lower coupling
- Clear responsibilities
- Easier future extension

A more complex architecture such as microservices was deliberately avoided because it would add operational and deployment complexity without providing significant value for the assessment's scope.

---

## 3. DTOs Instead of Exposing Entities

Request and response DTOs are used instead of exposing JPA entities directly through the API.

### Reasons

- Separates API contracts from persistence models
- Prevents accidental exposure of internal fields
- Allows API responses to evolve independently
- Makes validation explicit
- Improves maintainability

---

## 4. Salary History Model

Salary records are stored separately from employee records.

```text
Employee
   │
   └── Salary 1
   └── Salary 2
   └── Salary 3
```

### Decision

An employee can have multiple salary records with different effective dates.

### Why?

Overwriting the current salary would lose historical information.

Keeping separate salary records allows HR to view previous salary information and determine the latest salary based on the effective date.

---

## 5. Total Compensation

The application calculates:

```text
Total Compensation = Base Salary + Bonus
```

The calculation is performed using `BigDecimal` rather than floating-point numeric types.

### Why?

Salary values represent financial amounts, where predictable decimal arithmetic is important.

---

## 6. Multi-Currency Handling

Each salary record stores its own three-letter currency code.

For example:

```text
50,000 + 5,000 = 55,000 INR

80,000 + 10,000 = 90,000 USD
```

### Deliberate decision

The application does **not** convert currencies.

### Why?

Foreign exchange conversion is outside the salary-management scope and would introduce an external exchange-rate dependency.

Keeping currencies separate also avoids presenting misleading cross-currency salary comparisons.

---

## 7. Server-Side Pagination

Employee lists use server-side pagination.

### Why?

The assessment targets approximately 10,000 employees.

Returning all employees in a single API response would unnecessarily increase:

- Network payload
- Backend processing
- Browser memory usage
- Frontend rendering work

Instead, the frontend requests only the required page.

The API also supports server-side:

- Search
- Country filtering
- Sorting
- Sort direction

---

## 8. Database Constraints and Indexing

The database uses constraints and indexes to support data integrity and common queries.

Examples include:

- Primary key on employee ID
- Unique employee code
- Unique employee email
- Foreign key from salary records to employees
- Country indexing for filtering

### Why?

Constraints prevent invalid or duplicate data while indexes improve lookup and filtering performance.

---

## 9. Dashboard Aggregation

The dashboard provides:

- Total employee count
- Number of countries
- Employees with salary
- Country distribution
- Salary statistics by currency

Dashboard aggregation is performed by backend/database queries instead of sending all employee records to the browser.

### Why?

This reduces unnecessary data transfer and keeps aggregation work close to the data.

---

## 10. Validation and Error Handling

Validation is performed at the API boundary using Jakarta Bean Validation.

Examples:

- Required employee fields
- Valid email
- Non-negative salary
- Valid three-letter currency
- Required effective date

A centralized exception handler converts errors into consistent API responses.

HTTP status codes are used according to the type of failure:

```text
400 → Validation error
404 → Resource not found
409 → Duplicate resource
500 → Unexpected server error
```

### Why?

Consistent error handling makes the API easier for the Angular frontend to consume and prevents duplicated exception-handling logic.

---

## 11. Testing Strategy

The backend contains unit tests focused on business logic.

Tests cover areas including:

- Employee service behavior
- Salary service behavior
- Salary validation
- Salary calculations
- Dashboard service behavior
- Error scenarios

The final backend test run completed with:

```text
33 tests
0 failures
0 errors
0 skipped
```

### Trade-off

The focus was placed on meaningful service-level tests rather than attempting to maximize the number of tests.

The objective was fast, deterministic and understandable tests around important business behavior.

---

## 12. Performance Considerations

The system was tested using approximately 10,000 employee records.

The following approaches were used:

### Pagination

Only the requested employee page is returned.

### Database Filtering

Search and country filtering are performed through backend/database queries.

### Sorting

Sorting is performed server-side instead of loading the entire dataset into Angular.

### Aggregation

Dashboard statistics are calculated through backend/database aggregation.

### Performance observation

During local testing, the salary history API showed approximately:

```text
Time to first byte: ~12 ms
Total request time: ~17 ms
```

Other tested API requests generally completed within a low-millisecond range during local development.

These measurements are local development observations and should not be treated as production performance guarantees.

---

## 13. Why Microservices Were Not Used

A microservices architecture was considered unnecessary for this assessment.

### Reasoning

The application has a relatively focused domain:

- Employees
- Salaries
- Dashboard reporting

Splitting these into multiple independently deployed services would introduce:

- Service-to-service communication
- More deployment configuration
- Distributed debugging
- Additional infrastructure
- More operational complexity

A modular layered monolith provides sufficient separation for the current scale and is easier to develop, test and demonstrate.

The architecture can be decomposed into services later if actual scaling requirements justify it.

---

## 14. Features Deliberately Excluded

The implementation deliberately does not include:

- Payroll processing
- Tax calculation
- Currency conversion
- Attendance
- Leave management
- Benefits
- Recruitment
- Performance management
- Payslip generation
- External HR integrations
- Mobile application
- Advanced salary forecasting

### Reason

These features would increase the scope without being necessary for the core employee salary management workflow.

Keeping the scope focused allowed more attention to be given to correctness, testing, usability and performance.

---

## 15. Production Improvements

If the system were developed for production, the following improvements would be considered:

### Security

- Authentication
- Role-based authorization
- Secure password/session management
- API security
- HTTPS enforcement

### Infrastructure

- Production database configuration
- Application deployment
- Reverse proxy
- Centralized logging
- Monitoring and alerting

### Performance

- Additional database indexes based on production query patterns
- Caching for frequently requested dashboard data
- Query optimization based on real production metrics

### Reliability

- Database backup and recovery
- Health checks
- Graceful failure handling
- Centralized monitoring

These improvements were intentionally kept outside the core assessment implementation.

---

## 16. Summary of Key Trade-offs

| Decision | Choice | Reason |
|---|---|---|
| Backend | Spring Boot | Java preference and mature REST ecosystem |
| Frontend | Angular | Structured component-based UI |
| Database | MySQL | Relational employee/salary data |
| Architecture | Layered monolith | Simplicity and maintainability |
| Salary history | Separate records | Preserve historical salaries |
| Money type | BigDecimal | Accurate decimal calculations |
| Currency | Store separately | Avoid unnecessary FX dependency |
| Pagination | Server-side | Efficient handling of 10,000+ records |
| Dashboard | Database aggregation | Reduce data transfer |
| Validation | Bean Validation | Consistent input validation |
| Error handling | Global handler | Consistent API errors |
| Testing | Service-level unit tests | Fast and meaningful coverage |
| Microservices | Not used | Avoid unnecessary complexity |

## 17. Final Design Principle

The primary design principle was:

> Build the simplest maintainable solution that satisfies the assessment requirements while leaving a clear path for future scalability.

The implementation prioritizes correctness, clear separation of responsibilities, testability and practical performance over unnecessary architectural complexity.