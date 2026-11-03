package com.incubyte.employeesalary.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SalaryResponse {

    private Long id;
    private Long employeeId;
    private BigDecimal baseSalary;
    private BigDecimal bonus;
    private BigDecimal totalCompensation;
    private String currency;
    private LocalDate effectiveDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SalaryResponse() {
    }

    public SalaryResponse(
            Long id,
            Long employeeId,
            BigDecimal baseSalary,
            BigDecimal bonus,
            String currency,
            LocalDate effectiveDate,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.employeeId = employeeId;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
        this.totalCompensation = baseSalary.add(bonus);
        this.currency = currency;
        this.effectiveDate = effectiveDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public BigDecimal getBaseSalary() { return baseSalary; }
    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
        calculateTotalCompensation();
    }

    public BigDecimal getBonus() { return bonus; }
    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
        calculateTotalCompensation();
    }

    public BigDecimal getTotalCompensation() {
        return totalCompensation;
    }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public LocalDate getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    private void calculateTotalCompensation() {
        if (baseSalary != null && bonus != null) {
            totalCompensation = baseSalary.add(bonus);
        }
    }
}