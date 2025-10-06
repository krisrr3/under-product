package com.microcookie.api.controller;

import com.microcookie.api.domain.ApiCall;
import com.microcookie.api.domain.ProductFinancialStatus;
import com.microcookie.api.domain.funding.FundingStrategy;
import com.microcookie.api.service.ApiToProductRelationshipAnalyserService;
import com.microcookie.api.domain.report.ApiToProductCostEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Product API Cost Breakdown functionality
 */
@RestController
@RequestMapping("/api/cost-breakdown")
public class ProductApiCostBreakdownController {

    @Autowired
    private ApiToProductRelationshipAnalyserService service;

    /**
     * Get API to Product relationship analysis
     */
    @GetMapping("/api-to-product-report")
    public ResponseEntity<Map<String, List<ApiToProductCostEntry>>> getApiToProductReport() {
        try {
            Map<String, List<ApiToProductCostEntry>> report = service.generateApiToProductReport();
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Product API Cost Breakdown service is running");
    }
}