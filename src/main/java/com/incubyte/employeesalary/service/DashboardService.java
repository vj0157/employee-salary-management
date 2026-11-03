package com.incubyte.employeesalary.service;

import com.incubyte.employeesalary.dto.response.DashboardSummaryResponse;
import com.incubyte.employeesalary.repository.EmployeeRepository;
import com.incubyte.employeesalary.repository.EmployeeSalaryRepository;

import org.springframework.stereotype.Service;
import com.incubyte.employeesalary.dto.response.CountryDistributionResponse;
import java.util.List;
import com.incubyte.employeesalary.dto.response.SalaryStatisticsResponse;
import com.incubyte.employeesalary.repository.SalaryStatisticsProjection;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class DashboardService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeSalaryRepository salaryRepository;

    public DashboardService(
            EmployeeRepository employeeRepository,
            EmployeeSalaryRepository salaryRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.salaryRepository = salaryRepository;
    }

    public DashboardSummaryResponse getDashboardSummary() {

        long totalEmployees = employeeRepository.count();

        long totalCountries =
                employeeRepository.countDistinctCountries();

        long employeesWithSalary =
                salaryRepository.countEmployeesWithSalary();

        return new DashboardSummaryResponse(
                totalEmployees,
                totalCountries,
                employeesWithSalary
        );
    }
    public List<CountryDistributionResponse> getCountryDistribution() {
        return employeeRepository.findCountryDistribution();
    }

    public List<SalaryStatisticsResponse> getSalaryStatistics() {

        return salaryRepository.findCurrentSalaryStatistics()
                .stream()
                .map(statistics -> new SalaryStatisticsResponse(
                        statistics.getCurrency(),
                        statistics.getEmployeeCount(),
                        statistics.getAverageSalary(),
                        statistics.getMinimumSalary(),
                        statistics.getMaximumSalary()
                ))
                .collect(Collectors.toList());
    }
}