package com.incubyte.employeesalary.mapper;

import com.incubyte.employeesalary.dto.request.SalaryCreateRequest;
import com.incubyte.employeesalary.dto.request.SalaryUpdateRequest;
import com.incubyte.employeesalary.dto.response.SalaryResponse;
import com.incubyte.employeesalary.entity.Employee;
import com.incubyte.employeesalary.entity.EmployeeSalary;
import org.springframework.stereotype.Component;

@Component
public class SalaryMapper {

    // Convert salary create request into entity
    public EmployeeSalary toEntity(
            SalaryCreateRequest request,
            Employee employee
    ) {

        EmployeeSalary salary = new EmployeeSalary();

        salary.setEmployee(employee);
        salary.setBaseSalary(request.getBaseSalary());
        salary.setBonus(request.getBonus());
        salary.setCurrency(request.getCurrency());
        salary.setEffectiveDate(request.getEffectiveDate());

        return salary;
    }

    // Update existing salary entity
    public void updateEntity(
            EmployeeSalary salary,
            SalaryUpdateRequest request
    ) {
        salary.setBaseSalary(request.getBaseSalary());
        salary.setBonus(request.getBonus());
        salary.setCurrency(request.getCurrency());
        salary.setEffectiveDate(request.getEffectiveDate());
    }

    // Convert salary entity into response DTO
    public SalaryResponse toResponse(EmployeeSalary salary) {

        return new SalaryResponse(
                salary.getId(),
                salary.getEmployee().getId(),
                salary.getBaseSalary(),
                salary.getBonus(),
                salary.getCurrency(),
                salary.getEffectiveDate(),
                salary.getCreatedAt(),
                salary.getUpdatedAt()
        );
    }
}