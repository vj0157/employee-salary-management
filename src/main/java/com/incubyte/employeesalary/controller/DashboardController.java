package com.incubyte.employeesalary.controller;

import com.incubyte.employeesalary.dto.response.ApiResponse;
import com.incubyte.employeesalary.dto.response.CountryDistributionResponse;
import com.incubyte.employeesalary.dto.response.DashboardSummaryResponse;
import com.incubyte.employeesalary.dto.response.SalaryStatisticsResponse;
import com.incubyte.employeesalary.service.DashboardService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // Dashboard summary
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>>
    getDashboardSummary() {

        DashboardSummaryResponse response =
                dashboardService.getDashboardSummary();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Dashboard summary retrieved successfully",
                        response
                )
        );
    }

    // Country-wise employee distribution
    @GetMapping("/country-distribution")
    public ResponseEntity<ApiResponse<List<CountryDistributionResponse>>>
    getCountryDistribution() {

        List<CountryDistributionResponse> response =
                dashboardService.getCountryDistribution();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Country distribution retrieved successfully",
                        response
                )
        );
    }

    // Currency-wise salary statistics
    @GetMapping("/salary-statistics")
    public ResponseEntity<ApiResponse<List<SalaryStatisticsResponse>>>
    getSalaryStatistics() {

        List<SalaryStatisticsResponse> response =
                dashboardService.getSalaryStatistics();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Salary statistics retrieved successfully",
                        response
                )
        );
    }
}