package com.incubyte.employeesalary.mapper;

import com.incubyte.employeesalary.dto.request.EmployeeCreateRequest;
import com.incubyte.employeesalary.dto.request.EmployeeUpdateRequest;
import com.incubyte.employeesalary.dto.response.EmployeeResponse;
import com.incubyte.employeesalary.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    // Convert create request DTO into Employee entity
    public Employee toEntity(EmployeeCreateRequest request) {

        Employee employee = new Employee();

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setCountry(request.getCountry());

        return employee;
    }

    // Update existing Employee entity
    public void updateEntity(
            Employee employee,
            EmployeeUpdateRequest request
    ) {
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setCountry(request.getCountry());
    }

    // Convert Employee entity into response DTO
    public EmployeeResponse toResponse(Employee employee) {

        return new EmployeeResponse(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getCountry(),
                employee.getCreatedAt(),
                employee.getUpdatedAt()
        );
    }
}