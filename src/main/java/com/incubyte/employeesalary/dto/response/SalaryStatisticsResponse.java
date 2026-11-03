package com.incubyte.employeesalary.dto.response;

import java.math.BigDecimal;

public class SalaryStatisticsResponse {

    private String currency;
    private long employeeCount;
    private BigDecimal averageSalary;
    private BigDecimal minimumSalary;
    private BigDecimal maximumSalary;

    public SalaryStatisticsResponse() {
    }

    public SalaryStatisticsResponse(
            String currency,
            long employeeCount,
            BigDecimal averageSalary,
            BigDecimal minimumSalary,
            BigDecimal maximumSalary
    ) {
        this.currency = currency;
        this.employeeCount = employeeCount;
        this.averageSalary = averageSalary;
        this.minimumSalary = minimumSalary;
        this.maximumSalary = maximumSalary;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(long employeeCount) {
        this.employeeCount = employeeCount;
    }

    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }

    public BigDecimal getMinimumSalary() {
        return minimumSalary;
    }

    public void setMinimumSalary(BigDecimal minimumSalary) {
        this.minimumSalary = minimumSalary;
    }

    public BigDecimal getMaximumSalary() {
        return maximumSalary;
    }

    public void setMaximumSalary(BigDecimal maximumSalary) {
        this.maximumSalary = maximumSalary;
    }
}