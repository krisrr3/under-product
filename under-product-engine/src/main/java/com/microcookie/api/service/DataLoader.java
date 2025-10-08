package com.microcookie.api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microcookie.api.domain.ApiCall;
import com.microcookie.api.domain.ProductFinancialStatus;
import com.microcookie.api.domain.funding.FundingStrategy;
import com.microcookie.api.domain.ApiCost;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Service class to load test data from JSON files.
 */
@Service
public class DataLoader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ApiCall> loadApiCalls() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/api-calls-1.json")) {
            if (is == null) {
                throw new IOException("Resource not found: /api-calls-1.json");
            }
            return objectMapper.readValue(is, new TypeReference<List<ApiCall>>() {});
        }
    }

    public List<FundingStrategy> loadFundingDecisions() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/funding-decisions.json")) {
            if (is == null) {
                throw new IOException("Resource not found: /funding-decisions.json");
            }
            return objectMapper.readValue(is, new TypeReference<List<FundingStrategy>>() {});
        }
    }

    public List<ProductFinancialStatus> loadProductFinancialStatus() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/product-financial-status.json")) {
            if (is == null) {
                throw new IOException("Resource not found: /product-financial-status.json");
            }
            return objectMapper.readValue(is, new TypeReference<List<ProductFinancialStatus>>() {});
        }
    }

    public List<ApiCost> loadApiTcoData() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/api-tco-data.json")) {
            if (is == null) {
                throw new IOException("Resource not found: /api-tco-data.json");
            }
            return objectMapper.readValue(is, new TypeReference<List<ApiCost>>() {});
        }
    }
    

    /**
     * TODO This should ideally be a cached call as the data is not changed frequently
     * 
     * @return
     * @throws IOException
     */
    public List<String> getApiNames() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/api-tco-data.json")) {
            if (is == null) {
                throw new IOException("Resource not found: /api-tco-data.json");
            }
            List<String> apinames = objectMapper.readValue(is, new TypeReference<List<String>>() {});
            return apinames.stream()
                    .distinct()
                    .toList();
        }

        
    }
}