package com.incubyte.employeesalary.repository;

import com.incubyte.employeesalary.entity.Employee;
import com.incubyte.employeesalary.dto.response.CountryDistributionResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeCode(String employeeCode);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByEmployeeCode(String employeeCode);

    @Query("SELECT COUNT(DISTINCT e.country) FROM Employee e")
    long countDistinctCountries();

    @Query("""
        SELECT new com.incubyte.employeesalary.dto.response.CountryDistributionResponse(
            e.country,
            COUNT(e.id)
        )
        FROM Employee e
        GROUP BY e.country
        ORDER BY e.country
    """)
    List<CountryDistributionResponse> findCountryDistribution();

    // Search and filter employees with pagination
    @Query("""
        SELECT e FROM Employee e
        WHERE
            (:search IS NULL OR
             LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR
             LOWER(e.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR
             LOWER(e.employeeCode) LIKE LOWER(CONCAT('%', :search, '%')) OR
             LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%')))
        AND
            (:country IS NULL OR LOWER(e.country) = LOWER(:country))
    """)
    Page<Employee> searchEmployees(
            @Param("search") String search,
            @Param("country") String country,
            Pageable pageable
    );
}