package com.microcookie.api.service;


import com.microcookie.api.domain.report.ApiToProductCostEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ProductApiCostBreakdownOrchestrator
 */
@SpringBootTest
public class ApiToProductRelationshipAnalyserServiceTest {

    private ApiToProductRelationshipAnalyserService service;

    @BeforeEach
    public void setUp() throws Exception {
        service = new ApiToProductRelationshipAnalyserService();
        

    }

    @Test
    public void testGenerateApiToProductLink() throws Exception {
        Map<String, List<ApiToProductCostEntry>> result = service.generateApiToProductReport();
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Result should not be empty");

        List<ApiToProductCostEntry> paymentApiEntries = result.get("payments-api");
        assertNotNull(paymentApiEntries, "Payment API entries should not be null");
        assertFalse(paymentApiEntries.isEmpty(), "Payment API entries should not be empty");

        assertEquals("MPP",paymentApiEntries.get(0).getFundingStrategy());
    }
}