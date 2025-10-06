package com.microcookie.api.domain.report;

public class ApiToProductCostEntry {
    private String apiId;
    private String productId;
    private String fundingStrategy;
    private double tco;
    private String shareOfTco;
    private double costPerUnitCall;

    public String getShareOfTco() {
        return shareOfTco;
    }

    public void setShareOfTco(String shareOfTco) {
        this.shareOfTco = shareOfTco;
    }

    public String getFundingStrategy() {
        return fundingStrategy;
    }

    public void setFundingStrategy(String fundingStrategy) {
        this.fundingStrategy = fundingStrategy;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getTco() {
        return tco;
    }

    public void setTco(double tco) {
        this.tco = tco;
    }

    public double getCostPerUnitCall() {
        return costPerUnitCall;
    }

    public void setCostPerUnitCall(double costPerUnitCall) {
        this.costPerUnitCall = costPerUnitCall;
    }
}
