
package com.incubyte.employeesalary.service;

import com.incubyte.employeesalary.repository.EmployeeRepository;
import com.incubyte.employeesalary.repository.EmployeeSalaryRepository;
import com.incubyte.employeesalary.repository.SalaryStatisticsProjection;
import com.incubyte.employeesalary.dto.response.CountryDistributionResponse;
import com.incubyte.employeesalary.dto.response.SalaryStatisticsResponse;
import com.incubyte.employeesalary.dto.response.DashboardSummaryResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeSalaryRepository salaryRepository;

    @InjectMocks
    private DashboardService dashboardService;


    // ==========================================
    // DASHBOARD SUMMARY TEST CASES
    // ==========================================

    @Test
    void getDashboardSummary_shouldReturnCorrectCounts() {

        when(employeeRepository.count()).thenReturn(10000L);

        when(employeeRepository.countDistinctCountries())
                .thenReturn(12L);

        when(salaryRepository.countEmployeesWithSalary())
                .thenReturn(9850L);

        DashboardSummaryResponse result =
                dashboardService.getDashboardSummary();

        assertEquals(10000L, result.getTotalEmployees());
        assertEquals(12L, result.getTotalCountries());
        assertEquals(9850L, result.getEmployeesWithSalary());

        verify(employeeRepository).count();
        verify(employeeRepository).countDistinctCountries();
        verify(salaryRepository).countEmployeesWithSalary();
    }

    @Test
    void getDashboardSummary_whenDatabaseIsEmpty_shouldReturnZeroCounts() {

        when(employeeRepository.count()).thenReturn(0L);

        when(employeeRepository.countDistinctCountries())
                .thenReturn(0L);

        when(salaryRepository.countEmployeesWithSalary())
                .thenReturn(0L);

        DashboardSummaryResponse result =
                dashboardService.getDashboardSummary();

        assertEquals(0L, result.getTotalEmployees());
        assertEquals(0L, result.getTotalCountries());
        assertEquals(0L, result.getEmployeesWithSalary());
    }


    // ==========================================
    // COUNTRY DISTRIBUTION TEST CASES
    // ==========================================

    @Test
    void getCountryDistribution_shouldReturnCountryWiseCounts() {

        List<CountryDistributionResponse> countries = List.of(
                new CountryDistributionResponse("India", 4000L),
                new CountryDistributionResponse("USA", 2500L),
                new CountryDistributionResponse("UK", 1500L)
        );

        when(employeeRepository.findCountryDistribution())
                .thenReturn(countries);

        List<CountryDistributionResponse> result =
                dashboardService.getCountryDistribution();

        assertEquals(3, result.size());
        assertEquals("India", result.get(0).getCountry());
        assertEquals(4000L, result.get(0).getEmployeeCount());

        verify(employeeRepository).findCountryDistribution();
    }

    @Test
    void getCountryDistribution_whenNoEmployees_shouldReturnEmptyList() {

        when(employeeRepository.findCountryDistribution())
                .thenReturn(List.of());

        List<CountryDistributionResponse> result =
                dashboardService.getCountryDistribution();

        assertTrue(result.isEmpty());
    }


    // ==========================================
    // SALARY STATISTICS TEST CASES
    // ==========================================

    @Test
    void getSalaryStatistics_shouldReturnStatisticsGroupedByCurrency() {

        SalaryStatisticsProjection inr = createProjection(
                "INR", 100L, "60000.00", "25000.00", "150000.00"
        );

        SalaryStatisticsProjection usd = createProjection(
                "USD", 50L, "7000.00", "3000.00", "12000.00"
        );

        when(salaryRepository.findCurrentSalaryStatistics())
                .thenReturn(List.of(inr, usd));

        List<SalaryStatisticsResponse> result =
                dashboardService.getSalaryStatistics();

        assertEquals(2, result.size());

        assertEquals("INR", result.get(0).getCurrency());
        assertEquals(100L, result.get(0).getEmployeeCount());
        assertEquals(new BigDecimal("60000.00"),
                result.get(0).getAverageSalary());

        assertEquals("USD", result.get(1).getCurrency());
        assertEquals(50L, result.get(1).getEmployeeCount());
        assertEquals(new BigDecimal("7000.00"),
                result.get(1).getAverageSalary());

        verify(salaryRepository).findCurrentSalaryStatistics();
    }

    @Test
    void getSalaryStatistics_whenNoSalaryRecords_shouldReturnEmptyList() {

        when(salaryRepository.findCurrentSalaryStatistics())
                .thenReturn(List.of());

        List<SalaryStatisticsResponse> result =
                dashboardService.getSalaryStatistics();

        assertTrue(result.isEmpty());
    }


    // ==========================================
    // TEST DATA HELPER METHOD
    // ==========================================

    private SalaryStatisticsProjection createProjection(
            String currency,
            Long employeeCount,
            String averageSalary,
            String minimumSalary,
            String maximumSalary
    ) {

        SalaryStatisticsProjection projection =
                mock(SalaryStatisticsProjection.class);

        when(projection.getCurrency()).thenReturn(currency);
        when(projection.getEmployeeCount()).thenReturn(employeeCount);

        when(projection.getAverageSalary())
                .thenReturn(new BigDecimal(averageSalary));

        when(projection.getMinimumSalary())
                .thenReturn(new BigDecimal(minimumSalary));

        when(projection.getMaximumSalary())
                .thenReturn(new BigDecimal(maximumSalary));

        return projection;
    }
}
