package com.incubyte.employeesalary.dto.response;

import java.util.List;

public class EmployeeDetailResponse {

    private EmployeeResponse employee;
    private List<SalaryResponse> salaryHistory;
    private SalaryResponse currentSalary;

    public EmployeeDetailResponse() {
    }

    public EmployeeDetailResponse(
            EmployeeResponse employee,
            List<SalaryResponse> salaryHistory,
            SalaryResponse currentSalary
    ) {
        this.employee = employee;
        this.salaryHistory = salaryHistory;
        this.currentSalary = currentSalary;
    }

    public EmployeeResponse getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeResponse employee) {
        this.employee = employee;
    }

    public List<SalaryResponse> getSalaryHistory() {
        return salaryHistory;
    }

    public void setSalaryHistory(List<SalaryResponse> salaryHistory) {
        this.salaryHistory = salaryHistory;
    }

    public SalaryResponse getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(SalaryResponse currentSalary) {
        this.currentSalary = currentSalary;
    }
}