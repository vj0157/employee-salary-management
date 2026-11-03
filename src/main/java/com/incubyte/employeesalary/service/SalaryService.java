package com.incubyte.employeesalary.service;

import com.incubyte.employeesalary.entity.Employee;
import com.incubyte.employeesalary.entity.EmployeeSalary;
import com.incubyte.employeesalary.exception.ResourceNotFoundException;
import com.incubyte.employeesalary.repository.EmployeeRepository;
import com.incubyte.employeesalary.repository.EmployeeSalaryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {

    private final EmployeeSalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;

    public SalaryService(
            EmployeeSalaryRepository salaryRepository,
            EmployeeRepository employeeRepository
    ) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public List<EmployeeSalary> getSalariesByEmployeeId(Long employeeId) {

        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException(
                    "Employee not found with ID: " + employeeId
            );
        }

        return salaryRepository.findByEmployee_Id(employeeId);
    }

    @Transactional(readOnly = true)
    public Optional<EmployeeSalary> getLatestSalary(Long employeeId) {
        return salaryRepository
                .findFirstByEmployee_IdOrderByEffectiveDateDesc(employeeId);
    }

    @Transactional(readOnly = true)
    public EmployeeSalary getSalaryById(Long salaryId) {
        return salaryRepository.findById(salaryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Salary not found with ID: " + salaryId
                        )
                );
    }

    @Transactional
    public EmployeeSalary saveSalary(EmployeeSalary salary) {

        if (salary.getBaseSalary() == null ||
                salary.getBaseSalary().signum() < 0) {
            throw new IllegalArgumentException(
                    "Base salary cannot be negative"
            );
        }

        if (salary.getBonus() == null ||
                salary.getBonus().signum() < 0) {
            throw new IllegalArgumentException(
                    "Bonus cannot be negative"
            );
        }

        if (salary.getEffectiveDate() == null) {
            throw new IllegalArgumentException(
                    "Effective date is required"
            );
        }

        return salaryRepository.save(salary);
    }

    @Transactional
    public void deleteSalary(Long salaryId) {

        EmployeeSalary salary = getSalaryById(salaryId);

        salaryRepository.delete(salary);
    }
}