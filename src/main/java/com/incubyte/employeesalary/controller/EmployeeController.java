package com.incubyte.employeesalary.controller;

import com.incubyte.employeesalary.dto.request.EmployeeCreateRequest;
import com.incubyte.employeesalary.dto.request.EmployeeUpdateRequest;
import com.incubyte.employeesalary.dto.response.ApiResponse;
import com.incubyte.employeesalary.dto.response.EmployeeResponse;
import com.incubyte.employeesalary.entity.Employee;
import com.incubyte.employeesalary.exception.ResourceNotFoundException;
import com.incubyte.employeesalary.mapper.EmployeeMapper;
import com.incubyte.employeesalary.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(
            EmployeeService employeeService,
            EmployeeMapper employeeMapper
    ) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees() {

        List<EmployeeResponse> employees = employeeService.getAllEmployees()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employees retrieved successfully",
                        employees
                )
        );
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(
            @PathVariable Long id
    ) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID: " + id
                        )
                );

        EmployeeResponse response = employeeMapper.toResponse(employee);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employee retrieved successfully",
                        response
                )
        );
    }

    // Create employee
    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(
            @Valid @RequestBody EmployeeCreateRequest request
    ) {
        Employee employee = employeeMapper.toEntity(request);

        Employee savedEmployee =
                employeeService.createEmployee(employee);

        EmployeeResponse response =
                employeeMapper.toResponse(savedEmployee);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Employee created successfully",
                        response
                ));
    }

    // Update employee
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateRequest request
    ) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID: " + id
                        )
                );

        employeeMapper.updateEntity(employee, request);

        Employee updatedEmployee =
                employeeService.updateEmployee(id, employee);

        EmployeeResponse response =
                employeeMapper.toResponse(updatedEmployee);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employee updated successfully",
                        response
                )
        );
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(
            @PathVariable Long id
    ) {
        employeeService.deleteEmployee(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employee deleted successfully",
                        null
                )
        );
    }

    // Search, filter and paginate employees
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<EmployeeResponse>>> searchEmployees(

            @RequestParam(required = false) String search,

            @RequestParam(required = false) String country,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction
    ) {

        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Invalid pagination parameters"
            );
        }

        if (!List.of(
                "id", "firstName", "lastName",
                "employeeCode", "email", "country"
        ).contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field");
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<EmployeeResponse> employees =
                employeeService.searchEmployees(search, country, pageable)
                        .map(employeeMapper::toResponse);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employees retrieved successfully",
                        employees
                )
        );
    }
}