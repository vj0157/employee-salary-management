package com.incubyte.employeesalary.dto.response;

public class CountryDistributionResponse {

    private String country;
    private long employeeCount;

    public CountryDistributionResponse() {
    }

    public CountryDistributionResponse(
            String country,
            long employeeCount
    ) {
        this.country = country;
        this.employeeCount = employeeCount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(long employeeCount) {
        this.employeeCount = employeeCount;
    }
}