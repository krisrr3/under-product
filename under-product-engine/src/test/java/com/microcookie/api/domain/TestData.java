package com.microcookie.api.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microcookie.api.domain.funding.FundingStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * This class will be used to load the json files with test data of API calls.
 * 
 */
public class TestData {

    public static List<ApiCall> loadApiCalls() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = TestData.class.getResourceAsStream("/api-calls-1.json")) {
            if (is == null) {
                throw new IOException("Resource not found: /api-calls-1.json");
            }
            return mapper.readValue(is, new TypeReference<List<ApiCall>>() {});
        }
    }


    public static List<FundingStrategy> loadFundingDecisions() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = TestData.class.getResourceAsStream("/funding-decisions.json")) {
            if (is == null) {
                throw new IOException("Resource not found: /funding-decisions.json");
            }
            return mapper.readValue(is, new TypeReference<List<FundingStrategy>>() {});
        }
    }


    public static List<ProductFinancialStatus> loadProductFinancialStatus() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = TestData.class.getResourceAsStream("/product-financial-status.json")) {
            if (is == null) {
                throw new IOException("Resource not found: /product-financial-status.json");
            }
            return mapper.readValue(is, new TypeReference<List<ProductFinancialStatus>>() {});
        }
    }
}
