package com.incubyte.employeesalary.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalaryUpdateRequest {

    @NotNull(message = "Base salary is required")
    @DecimalMin(value = "0.00", message = "Base salary cannot be negative")
    private BigDecimal baseSalary;

    @NotNull(message = "Bonus is required")
    @DecimalMin(value = "0.00", message = "Bonus cannot be negative")
    private BigDecimal bonus;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter code")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "Currency must contain 3 uppercase letters"
    )
    private String currency;

    @NotNull(message = "Effective date is required")
    private LocalDate effectiveDate;

    public SalaryUpdateRequest() {
    }

    // Getters and Setters

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}