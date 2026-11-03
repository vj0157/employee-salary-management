# AI Workflow & Development Process

## 1. Overview

AI-assisted development was used throughout the Employee Salary Management System to support planning, implementation, debugging, testing and documentation.

The primary AI assistant used during development was ChatGPT.

AI was used as a development aid rather than as an autonomous replacement for engineering decisions. Generated code and recommendations were reviewed, executed and verified before being accepted into the project.

## 2. Development Workflow

The development process followed an iterative workflow:

```text
Requirement
    ↓
AI-assisted planning
    ↓
Implementation
    ↓
Run application
    ↓
Test / verify
    ↓
Debug issues
    ↓
Refine implementation
    ↓
Commit working changes
```

Each significant change was validated against the application's requirements before being retained.

## 3. Areas Where AI Was Used

### Requirements and Planning

AI was used to help:

- Break the assessment requirements into implementation tasks
- Define the application scope
- Identify features required for the HR Manager workflow
- Identify features deliberately excluded from the assessment
- Structure the requirements documentation

### Architecture and Design

AI assistance was used for:

- Layered Spring Boot architecture
- Controller/service/repository separation
- DTO and mapper design
- Employee and salary data relationships
- REST API structure
- Server-side pagination, filtering and sorting
- Dashboard aggregation design
- Performance considerations for approximately 10,000 employees

Final architectural decisions were reviewed against the assessment requirements and the implemented application.

### Backend Development

AI assistance was used during implementation and refinement of:

- Spring Boot controllers
- Service classes
- Repository queries
- DTOs
- Entity models
- Mappers
- Validation
- Exception handling
- Dashboard APIs
- Salary management APIs

Generated or suggested code was tested locally before being accepted.

### Frontend Development

AI assistance was used for:

- Angular component structure
- Routing
- HTTP service integration
- Employee management UI
- Salary management UI
- Dashboard UI
- Pagination and filtering integration
- Form validation
- Connecting frontend components to backend APIs

The resulting features were manually tested through the running Angular application.

### Testing and Debugging

AI was used to assist with:

- Identifying potential test scenarios
- Writing and improving unit tests
- Debugging compilation errors
- Debugging API and validation issues
- Investigating database-related errors
- Reviewing failed tests
- Improving error handling

Tests were executed locally to verify the actual behavior rather than relying only on AI-generated expectations.

### Documentation

AI assistance was used to structure and improve:

- `README.md`
- `REQUIREMENTS.md`
- `ARCHITECTURE.md`
- Development and design documentation

Documentation was reviewed against the actual implementation before being added to the repository.

## 4. Representative Prompt Categories

The development process involved prompts covering areas such as:

### Architecture

> Design a maintainable Spring Boot architecture for an employee salary management system supporting approximately 10,000 employees.

### API Design

> Define REST APIs for employee CRUD operations and salary history management, including validation and appropriate HTTP responses.

### Testing

> Identify meaningful unit test cases for the salary service, including validation, salary calculation and error scenarios.

### Debugging

> Analyze this Java/Spring Boot error and explain the root cause and the safest fix.

### Frontend Integration

> Help integrate the Angular employee management UI with the Spring Boot REST APIs, including pagination, search, filtering and sorting.

### Documentation

> Review the implemented system and create clear technical documentation covering architecture, design decisions, performance and trade-offs.

These prompts were adapted iteratively based on the actual application state and errors encountered during development.

## 5. Human Verification

AI-generated suggestions were not accepted blindly.

The development process included human verification through:

- Running the application locally
- Executing backend unit tests
- Testing REST APIs
- Testing the Angular UI
- Testing validation and error scenarios
- Testing salary calculations
- Testing employee search and pagination
- Testing the application with approximately 10,000 employee records
- Reviewing generated documentation
- Reviewing Git changes before committing

The final implementation therefore reflects both AI-assisted development and manual engineering verification.

## 6. AI and Code Quality

AI was treated as an engineering assistant.

The following principles were followed:

- Do not assume generated code is correct
- Compile and run generated code
- Execute relevant tests
- Verify API behavior
- Review database queries
- Check edge cases
- Keep implementation aligned with the assessment scope
- Avoid adding unnecessary functionality
- Prefer understandable and maintainable code

## 7. Benefits of AI Assistance

AI assistance helped reduce development time by providing:

- Faster exploration of implementation approaches
- Faster debugging of errors
- Test-case suggestions
- Documentation assistance
- Code review suggestions
- Faster iteration between frontend and backend

The developer remained responsible for selecting the final approach and verifying the resulting behavior.

## 8. Limitations

AI-generated code can contain incorrect assumptions or implementation mistakes.

Examples of verification required during development included:

- Compilation errors
- Unit test failures
- Database constraint issues
- API integration issues
- Angular integration issues
- Validation behavior
- Performance verification

These issues were resolved through local execution, testing and iterative refinement.

## 9. Final Approach

The final development approach was:

```text
AI assistance
    +
Developer decisions
    +
Local implementation
    +
Automated tests
    +
Manual verification
    =
Final solution
```

AI accelerated the development process while keeping engineering decisions, verification and final acceptance under developer control.