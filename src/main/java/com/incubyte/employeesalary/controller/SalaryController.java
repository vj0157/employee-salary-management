package com.incubyte.employeesalary.controller;

import com.incubyte.employeesalary.dto.request.SalaryCreateRequest;
import com.incubyte.employeesalary.dto.request.SalaryUpdateRequest;
import com.incubyte.employeesalary.dto.response.ApiResponse;
import com.incubyte.employeesalary.dto.response.SalaryResponse;

import com.incubyte.employeesalary.entity.Employee;
import com.incubyte.employeesalary.entity.EmployeeSalary;

import com.incubyte.employeesalary.mapper.SalaryMapper;
import com.incubyte.employeesalary.service.EmployeeService;
import com.incubyte.employeesalary.service.SalaryService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SalaryController {

    private final EmployeeService employeeService;
    private final SalaryService salaryService;
    private final SalaryMapper salaryMapper;

    public SalaryController(
            EmployeeService employeeService,
            SalaryService salaryService,
            SalaryMapper salaryMapper
    ) {
        this.employeeService = employeeService;
        this.salaryService = salaryService;
        this.salaryMapper = salaryMapper;
    }

    // Get salary history
    @GetMapping("/employees/{employeeId}/salaries")
    public ResponseEntity<ApiResponse<List<SalaryResponse>>> getSalaryHistory(
            @PathVariable Long employeeId
    ) {

        List<SalaryResponse> salaries =
                salaryService.getSalariesByEmployeeId(employeeId)
                        .stream()
                        .map(salaryMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Salary history retrieved successfully",
                        salaries
                )
        );
    }

    // Create salary
    @PostMapping("/employees/{employeeId}/salaries")
    public ResponseEntity<ApiResponse<SalaryResponse>> createSalary(
            @PathVariable Long employeeId,
            @Valid @RequestBody SalaryCreateRequest request
    ) {

        Employee employee = employeeService.getEmployeeById(employeeId)
                .orElseThrow(() ->
                        new com.incubyte.employeesalary.exception.ResourceNotFoundException(
                                "Employee not found with ID: " + employeeId
                        )
                );

        EmployeeSalary salary = salaryMapper.toEntity(request, employee);

        EmployeeSalary savedSalary = salaryService.saveSalary(salary);

        SalaryResponse response = salaryMapper.toResponse(savedSalary);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Salary created successfully",
                        response
                ));
    }

    // Update salary record
    @PutMapping("/salaries/{salaryId}")
    public ResponseEntity<ApiResponse<SalaryResponse>> updateSalary(
            @PathVariable Long salaryId,
            @Valid @RequestBody SalaryUpdateRequest request
    ) {

        EmployeeSalary salary = salaryService.getSalaryById(salaryId);

        salaryMapper.updateEntity(salary, request);

        EmployeeSalary updatedSalary = salaryService.saveSalary(salary);

        SalaryResponse response = salaryMapper.toResponse(updatedSalary);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Salary updated successfully",
                        response
                )
        );
    }

    // Delete salary record
    @DeleteMapping("/salaries/{salaryId}")
    public ResponseEntity<ApiResponse<Void>> deleteSalary(
            @PathVariable Long salaryId
    ) {

        salaryService.deleteSalary(salaryId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Salary deleted successfully",
                        null
                )
        );
    }
}