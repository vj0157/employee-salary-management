package com.incubyte.employeesalary.service;

import com.incubyte.employeesalary.entity.Employee;
import com.incubyte.employeesalary.exception.DuplicateResourceException;
import com.incubyte.employeesalary.exception.ResourceNotFoundException;
import com.incubyte.employeesalary.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getEmployeeByCode(String employeeCode) {
        return employeeRepository.findByEmployeeCode(employeeCode);
    }

    @Transactional
    public Employee createEmployee(Employee employee) {

        if (employeeRepository.existsByEmployeeCode(
                employee.getEmployeeCode())) {
            throw new DuplicateResourceException(
                    "Employee code already exists");
        }

        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new DuplicateResourceException(
                    "Employee email already exists");
        }

        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID: " + id));

        if (employeeRepository.existsByEmailAndIdNot(
                updatedEmployee.getEmail(), id)) {
            throw new DuplicateResourceException(
                    "Employee email already exists");
        }

        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setCountry(updatedEmployee.getCountry());

        return employeeRepository.save(existingEmployee);
    }

    @Transactional
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID: " + id));

        employeeRepository.delete(employee);
    }

    @Transactional(readOnly = true)
    public Page<Employee> searchEmployees(
            String search,
            String country,
            Pageable pageable
    ) {
        return employeeRepository.searchEmployees(
                search,
                country,
                pageable
        );
    }
}