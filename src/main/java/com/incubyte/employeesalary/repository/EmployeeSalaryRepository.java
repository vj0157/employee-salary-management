package com.incubyte.employeesalary.repository;

import com.incubyte.employeesalary.entity.EmployeeSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.List;
public interface EmployeeSalaryRepository
        extends JpaRepository<EmployeeSalary, Long> {

    List<EmployeeSalary> findByEmployee_Id(Long employeeId);

    Optional<EmployeeSalary> findFirstByEmployee_IdOrderByEffectiveDateDesc(
            Long employeeId
    );

    @Query("SELECT COUNT(DISTINCT s.employee.id) FROM EmployeeSalary s")
    long countEmployeesWithSalary();

    @Query(value = """
    SELECT
        s.currency AS currency,
        COUNT(DISTINCT s.employee_id) AS employeeCount,
        AVG(s.base_salary + s.bonus) AS averageSalary,
        MIN(s.base_salary + s.bonus) AS minimumSalary,
        MAX(s.base_salary + s.bonus) AS maximumSalary
    FROM employee_salaries s
    WHERE s.effective_date <= CURRENT_DATE
      AND NOT EXISTS (
          SELECT 1
          FROM employee_salaries newer
          WHERE newer.employee_id = s.employee_id
            AND newer.effective_date <= CURRENT_DATE
            AND (
                newer.effective_date > s.effective_date
                OR (
                    newer.effective_date = s.effective_date
                    AND newer.id > s.id
                )
            )
      )
    GROUP BY s.currency
    ORDER BY s.currency
    """, nativeQuery = true)
    List<SalaryStatisticsProjection> findCurrentSalaryStatistics();
}