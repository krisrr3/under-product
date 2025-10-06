package com.microcookie.api.service;

import com.microcookie.api.domain.ApiCall;
import com.microcookie.api.domain.ApiCost;
import com.microcookie.api.domain.ProductFinancialStatus;
import com.microcookie.api.domain.funding.FundingStrategy;
import org.springframework.stereotype.Service;
import com.microcookie.api.domain.report.ApiToProductCostEntry;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Orchestrator class that generates API cost breakdown per Product
 * based on different funding strategies and API usage patterns.
 */
@Service
public class ApiToProductRelationshipAnalyserService {


    private final DataLoader dataLoader = new DataLoader();
    private List<ApiCall> apiCalls;
    private List<FundingStrategy> fundingStrategies;
    private List<ProductFinancialStatus> productFinancialStatus;
    private List<ApiCost> apiTcoData;


    public ApiToProductRelationshipAnalyserService() throws IOException {
        this.apiCalls = dataLoader.loadApiCalls();
        this.fundingStrategies = dataLoader.loadFundingDecisions();
        this.productFinancialStatus = dataLoader.loadProductFinancialStatus();
        this.apiTcoData = dataLoader.loadApiTcoData();
    }



    public  Map<String, List<ApiToProductCostEntry>>  generateApiToProductReport() {

        Map<String, List<ApiToProductCostEntry>> apiToProductCostMap = new HashMap<>();
        for (ApiCall apiCall : apiCalls) {
            System.out.println("API Call: " + apiCall);

            // check if the API entry is new in the map
            if (!apiToProductCostMap.containsKey(apiCall.getCalledLayer())) {
                List<ApiToProductCostEntry> costEntries = new ArrayList<>();
                ApiToProductCostEntry newCostEntry = new ApiToProductCostEntry();
                newCostEntry.setApiId(apiCall.getCalledLayer());
                newCostEntry.setProductId(apiCall.getOriginatingProduct());
                String fundingStrategy = findFundingStrategy(apiCall.getCalledLayer());
                newCostEntry.setFundingStrategy(fundingStrategy);
                newCostEntry.setTco(findTco(apiCall.getCalledLayer()));
                newCostEntry.setShareOfTco(findShareOfTco(fundingStrategy, apiCall.getCalledLayer()));
                costEntries.add(newCostEntry);
                apiToProductCostMap.put(apiCall.getCalledLayer(), costEntries);
            } else {
                System.out.println("API already exists in the map: " + apiCall.getCalledLayer());
                //put the API being called as key in the map
                List<ApiToProductCostEntry> existingCostEntries = apiToProductCostMap.get(apiCall.getCalledLayer());
                ApiToProductCostEntry newCostEntry = new ApiToProductCostEntry();
                newCostEntry.setApiId(apiCall.getCalledLayer());
                newCostEntry.setProductId(apiCall.getOriginatingProduct());
                String fundingStrategy = findFundingStrategy(apiCall.getCalledLayer());
                newCostEntry.setFundingStrategy(fundingStrategy);
                newCostEntry.setTco(findTco(apiCall.getCalledLayer()));
                newCostEntry.setShareOfTco(findShareOfTco(fundingStrategy, apiCall.getCalledLayer()));
                existingCostEntries.add(newCostEntry);
                apiToProductCostMap.put(apiCall.getCalledLayer(), existingCostEntries);
            }
            
        }

        return apiToProductCostMap;
    }


    private String findFundingStrategy(String apiId) {
        for (FundingStrategy strategy : fundingStrategies) {
            if (strategy.getApiId().equals(apiId)) {
                return strategy.getFundingStrategyType();
            }
        }
        return "Unknown";
    }

    private double findTco(String apiId) {
        for (ApiCost apiCost : apiTcoData) {
            if (apiCost.getId().equals(apiId)) {
                return apiCost.getTco();
            }
        }
        return 0.0;
    }

    private String findShareOfTco(String fundingStrategy, String apiId) {
       
        switch (fundingStrategy) {
            case "Most Profitable Product":
                // Assuming the most profitable product pays 100% of the TCO
                return "100%";
            case "Equal Share":
                System.out.println("Calculating Equal Share for API ID: " + apiId);
                return "To be implemented";
            case "Pay by Rate":
                System.out.println("Calculating Pay by Rate for API ID: " + apiId);
                return "To be implemented";
            default:
                return "Not Covered";
        }
    }
}