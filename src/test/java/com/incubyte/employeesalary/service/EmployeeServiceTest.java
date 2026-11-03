
package com.incubyte.employeesalary.service;

import com.incubyte.employeesalary.entity.Employee;
import com.incubyte.employeesalary.repository.EmployeeRepository;
import com.incubyte.employeesalary.exception.DuplicateResourceException;
import com.incubyte.employeesalary.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;


    // ==========================================
    // CREATE EMPLOYEE TEST CASES
    // ==========================================

    @Test
    void createEmployee_whenValidEmployee_shouldSaveEmployee() {

        Employee employee = createEmployee(
                "EMP004", "Rahul", "Sharma",
                "rahul@example.com", "India"
        );

        when(employeeRepository.existsByEmployeeCode("EMP004"))
                .thenReturn(false);

        when(employeeRepository.existsByEmail("rahul@example.com"))
                .thenReturn(false);

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        Employee result = employeeService.createEmployee(employee);

        assertSame(employee, result);
        verify(employeeRepository).save(employee);
    }

    @Test
    void createEmployee_whenEmployeeCodeAlreadyExists_shouldThrowException() {

        Employee employee = createEmployee(
                "EMP001", "Rahul", "Sharma",
                "rahul@example.com", "India"
        );

        when(employeeRepository.existsByEmployeeCode("EMP001"))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> employeeService.createEmployee(employee)
        );

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void createEmployee_whenEmailAlreadyExists_shouldThrowException() {

        Employee employee = createEmployee(
                "EMP005", "Rahul", "Sharma",
                "amit@example.com", "India"
        );

        when(employeeRepository.existsByEmployeeCode("EMP005"))
                .thenReturn(false);

        when(employeeRepository.existsByEmail("amit@example.com"))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> employeeService.createEmployee(employee)
        );

        verify(employeeRepository, never()).save(any(Employee.class));
    }


    // ==========================================
    // READ EMPLOYEE TEST CASES
    // ==========================================

    @Test
    void getAllEmployees_shouldReturnEmployeeList() {

        List<Employee> employees = List.of(
                createEmployee("EMP001", "Amit", "Sharma",
                        "amit@example.com", "India"),
                createEmployee("EMP002", "John", "Smith",
                        "john@example.com", "USA")
        );

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
        verify(employeeRepository).findAll();
    }

    @Test
    void getEmployeeById_whenEmployeeExists_shouldReturnEmployee() {

        Employee employee = createEmployee(
                "EMP001", "Amit", "Sharma",
                "amit@example.com", "India"
        );

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.getEmployeeById(1L);

        assertTrue(result.isPresent());
        assertSame(employee, result.get());
    }

    @Test
    void getEmployeeById_whenEmployeeDoesNotExist_shouldReturnEmpty() {

        when(employeeRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<Employee> result = employeeService.getEmployeeById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getEmployeeByCode_whenEmployeeExists_shouldReturnEmployee() {

        Employee employee = createEmployee(
                "EMP001", "Amit", "Sharma",
                "amit@example.com", "India"
        );

        when(employeeRepository.findByEmployeeCode("EMP001"))
                .thenReturn(Optional.of(employee));

        Optional<Employee> result =
                employeeService.getEmployeeByCode("EMP001");

        assertTrue(result.isPresent());
        assertSame(employee, result.get());
    }


    // ==========================================
    // UPDATE EMPLOYEE TEST CASES
    // ==========================================

    @Test
    void updateEmployee_whenValidEmployee_shouldUpdateEmployee() {

        Employee existingEmployee = createEmployee(
                "EMP001", "Amit", "Sharma",
                "amit@example.com", "India"
        );

        Employee updatedEmployee = createEmployee(
                "EMP001", "Amit", "Patil",
                "amit.patil@example.com", "India"
        );

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(existingEmployee));

        when(employeeRepository.existsByEmailAndIdNot(
                "amit.patil@example.com", 1L))
                .thenReturn(false);

        when(employeeRepository.save(existingEmployee))
                .thenReturn(existingEmployee);

        Employee result = employeeService.updateEmployee(
                1L, updatedEmployee
        );

        assertEquals("Patil", result.getLastName());
        assertEquals("amit.patil@example.com", result.getEmail());

        verify(employeeRepository).save(existingEmployee);
    }

    @Test
    void updateEmployee_whenEmployeeDoesNotExist_shouldThrowException() {

        Employee updatedEmployee = createEmployee(
                "EMP001", "Amit", "Patil",
                "amit.patil@example.com", "India"
        );

        when(employeeRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.updateEmployee(99L, updatedEmployee)
        );

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void updateEmployee_whenEmailAlreadyExists_shouldThrowException() {

        Employee existingEmployee = createEmployee(
                "EMP001", "Amit", "Sharma",
                "amit@example.com", "India"
        );

        Employee updatedEmployee = createEmployee(
                "EMP001", "Amit", "Patil",
                "john@example.com", "India"
        );

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(existingEmployee));

        when(employeeRepository.existsByEmailAndIdNot(
                "john@example.com", 1L))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> employeeService.updateEmployee(1L, updatedEmployee)
        );

        verify(employeeRepository, never()).save(any(Employee.class));
    }


    // ==========================================
    // DELETE EMPLOYEE TEST CASES
    // ==========================================

    @Test
    void deleteEmployee_whenEmployeeExists_shouldDeleteEmployee() {

        Employee employee = createEmployee(
                "EMP001", "Amit", "Sharma",
                "amit@example.com", "India"
        );

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).delete(employee);
    }

    @Test
    void deleteEmployee_whenEmployeeDoesNotExist_shouldThrowException() {

        when(employeeRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.deleteEmployee(99L)
        );

        verify(employeeRepository, never()).delete(any(Employee.class));
    }


    // ==========================================
    // TEST DATA HELPER METHOD
    // ==========================================

    private Employee createEmployee(
            String employeeCode,
            String firstName,
            String lastName,
            String email,
            String country
    ) {

        Employee employee = new Employee();

        employee.setEmployeeCode(employeeCode);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setCountry(country);

        return employee;
    }
}
