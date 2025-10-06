package com.microcookie.api.domain;

import org.junit.jupiter.api.Test;

import com.microcookie.api.domain.funding.FundingStrategy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import com.microcookie.api.domain.ProductFinancialStatus;

public class ApiCallTest {

    List<ApiCall> apiCalls = new ArrayList<>();
    List<FundingStrategy> fundingStrategies = new ArrayList<>();
    List<ProductFinancialStatus> productFinancialStatusList = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception {
        apiCalls = TestData.loadApiCalls();
        fundingStrategies = TestData.loadFundingDecisions();
        productFinancialStatusList = TestData.loadProductFinancialStatus();
    }

    @Test
    public void testApiCall_fundingStrategyMostProfitablePays() {
        // Test logic for creating an ApiCall instance
    }

    @Test
    public void testApiCall_fundingStrategyEqualShare() {
        // Test logic for creating an ApiCall instance
    }

    @Test
    public void testApiCall_fundingStrategyPayByRate() {
        // Test logic for creating an ApiCall instance
    }
}
