package com.incubyte.employeesalary.dto.response;

public class DashboardSummaryResponse {

    private long totalEmployees;
    private long totalCountries;
    private long employeesWithSalary;

    public DashboardSummaryResponse() {
    }

    public DashboardSummaryResponse(
            long totalEmployees,
            long totalCountries,
            long employeesWithSalary
    ) {
        this.totalEmployees = totalEmployees;
        this.totalCountries = totalCountries;
        this.employeesWithSalary = employeesWithSalary;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getTotalCountries() {
        return totalCountries;
    }

    public void setTotalCountries(long totalCountries) {
        this.totalCountries = totalCountries;
    }

    public long getEmployeesWithSalary() {
        return employeesWithSalary;
    }

    public void setEmployeesWithSalary(long employeesWithSalary) {
        this.employeesWithSalary = employeesWithSalary;
    }
}