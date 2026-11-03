package com.incubyte.employeesalary.repository;

import java.math.BigDecimal;

public interface SalaryStatisticsProjection {

    String getCurrency();

    Long getEmployeeCount();

    BigDecimal getAverageSalary();

    BigDecimal getMinimumSalary();

    BigDecimal getMaximumSalary();
}