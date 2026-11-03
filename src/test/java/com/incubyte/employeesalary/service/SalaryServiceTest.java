
package com.incubyte.employeesalary.service;

import com.incubyte.employeesalary.entity.Employee;
import com.incubyte.employeesalary.entity.EmployeeSalary;
import com.incubyte.employeesalary.repository.EmployeeRepository;
import com.incubyte.employeesalary.repository.EmployeeSalaryRepository;
import com.incubyte.employeesalary.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaryServiceTest {

    @Mock
    private EmployeeSalaryRepository salaryRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private SalaryService salaryService;


    // ==========================================
    // READ SALARY HISTORY TEST CASES
    // ==========================================

    @Test
    void getSalariesByEmployeeId_whenEmployeeExists_shouldReturnSalaryHistory() {

        Employee employee = new Employee();

        EmployeeSalary salary1 = createSalary(
                employee, "60000", "5000", "INR"
        );

        EmployeeSalary salary2 = createSalary(
                employee, "65000", "5000", "INR"
        );

        when(employeeRepository.existsById(1L))
                .thenReturn(true);

        when(salaryRepository.findByEmployee_Id(1L))
                .thenReturn(List.of(salary1, salary2));

        List<EmployeeSalary> result =
                salaryService.getSalariesByEmployeeId(1L);

        assertEquals(2, result.size());

        verify(employeeRepository).existsById(1L);
        verify(salaryRepository).findByEmployee_Id(1L);
    }
    @Test
    void getSalariesByEmployeeId_whenEmployeeDoesNotExist_shouldThrowException() {

        when(employeeRepository.existsById(99L))
                .thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> salaryService.getSalariesByEmployeeId(99L)
        );

        verify(employeeRepository).existsById(99L);

        verify(salaryRepository, never())
                .findByEmployee_Id(anyLong());
    }

    // ==========================================
    // LATEST SALARY TEST CASES
    // ==========================================

    @Test
    void getLatestSalary_whenSalaryExists_shouldReturnLatestSalary() {

        Employee employee = new Employee();

        EmployeeSalary salary = createSalary(
                employee, "70000", "10000", "INR"
        );

        when(salaryRepository
                .findFirstByEmployee_IdOrderByEffectiveDateDesc(1L))
                .thenReturn(Optional.of(salary));

        Optional<EmployeeSalary> result =
                salaryService.getLatestSalary(1L);

        assertTrue(result.isPresent());
        assertSame(salary, result.get());

        verify(salaryRepository)
                .findFirstByEmployee_IdOrderByEffectiveDateDesc(1L);
    }

    @Test
    void getLatestSalary_whenSalaryDoesNotExist_shouldReturnEmpty() {

        when(salaryRepository
                .findFirstByEmployee_IdOrderByEffectiveDateDesc(1L))
                .thenReturn(Optional.empty());

        Optional<EmployeeSalary> result =
                salaryService.getLatestSalary(1L);

        assertTrue(result.isEmpty());
    }


    // ==========================================
    // GET SALARY BY ID TEST CASES
    // ==========================================

    @Test
    void getSalaryById_whenSalaryExists_shouldReturnSalary() {

        EmployeeSalary salary = createSalary(
                new Employee(), "60000", "5000", "INR"
        );

        when(salaryRepository.findById(1L))
                .thenReturn(Optional.of(salary));

        EmployeeSalary result = salaryService.getSalaryById(1L);

        assertSame(salary, result);
    }

    @Test
    void getSalaryById_whenSalaryDoesNotExist_shouldThrowException() {

        when(salaryRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> salaryService.getSalaryById(99L)
        );
    }


    // ==========================================
    // SAVE SALARY TEST CASES
    // ==========================================

    @Test
    void saveSalary_whenValidSalary_shouldSaveSalary() {

        EmployeeSalary salary = createSalary(
                new Employee(), "60000", "5000", "INR"
        );

        when(salaryRepository.save(salary))
                .thenReturn(salary);

        EmployeeSalary result = salaryService.saveSalary(salary);

        assertSame(salary, result);
        verify(salaryRepository).save(salary);
    }

    @Test
    void saveSalary_whenBaseSalaryIsNegative_shouldThrowException() {

        EmployeeSalary salary = createSalary(
                new Employee(), "-1000", "5000", "INR"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> salaryService.saveSalary(salary)
        );

        verify(salaryRepository, never())
                .save(any(EmployeeSalary.class));
    }

    @Test
    void saveSalary_whenBonusIsNegative_shouldThrowException() {

        EmployeeSalary salary = createSalary(
                new Employee(), "60000", "-500", "INR"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> salaryService.saveSalary(salary)
        );

        verify(salaryRepository, never())
                .save(any(EmployeeSalary.class));
    }

    @Test
    void saveSalary_whenEffectiveDateIsMissing_shouldThrowException() {

        EmployeeSalary salary = createSalary(
                new Employee(), "60000", "5000", "INR"
        );

        salary.setEffectiveDate(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> salaryService.saveSalary(salary)
        );

        verify(salaryRepository, never())
                .save(any(EmployeeSalary.class));
    }

    @Test
    void saveSalary_whenBaseSalaryIsMissing_shouldThrowException() {

        EmployeeSalary salary = createSalary(
                new Employee(), "60000", "5000", "INR"
        );

        salary.setBaseSalary(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> salaryService.saveSalary(salary)
        );

        verify(salaryRepository, never())
                .save(any(EmployeeSalary.class));
    }

    @Test
    void saveSalary_whenBonusIsMissing_shouldThrowException() {

        EmployeeSalary salary = createSalary(
                new Employee(), "60000", "5000", "INR"
        );

        salary.setBonus(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> salaryService.saveSalary(salary)
        );

        verify(salaryRepository, never())
                .save(any(EmployeeSalary.class));
    }


    // ==========================================
    // DELETE SALARY TEST CASES
    // ==========================================

    @Test
    void deleteSalary_whenSalaryExists_shouldDeleteSalary() {

        EmployeeSalary salary = createSalary(
                new Employee(), "60000", "5000", "INR"
        );

        when(salaryRepository.findById(1L))
                .thenReturn(Optional.of(salary));

        salaryService.deleteSalary(1L);

        verify(salaryRepository).delete(salary);
    }

    @Test
    void deleteSalary_whenSalaryDoesNotExist_shouldThrowException() {

        when(salaryRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> salaryService.deleteSalary(99L)
        );

        verify(salaryRepository, never())
                .delete(any(EmployeeSalary.class));
    }


    // ==========================================
    // TEST DATA HELPER METHOD
    // ==========================================

    private EmployeeSalary createSalary(
            Employee employee,
            String baseSalary,
            String bonus,
            String currency
    ) {

        EmployeeSalary salary = new EmployeeSalary();

        salary.setEmployee(employee);
        salary.setBaseSalary(new BigDecimal(baseSalary));
        salary.setBonus(new BigDecimal(bonus));
        salary.setCurrency(currency);
        salary.setEffectiveDate(LocalDate.of(2026, 11, 1));

        return salary;
    }
}
