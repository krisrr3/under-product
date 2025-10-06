package com.microcookie.api.domain;

public class ProductFinancialStatus {
    private String productId;
    private double revenue;
    private double cost;
    private double profit;

    // Constructors
    public ProductFinancialStatus() {}

    public ProductFinancialStatus(String productId, double revenue, double cost) {
        this.productId = productId;
        this.revenue = revenue;
        this.cost = cost;
        this.profit = revenue - cost;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
        updateProfit();
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
        updateProfit();
    }

    public double getProfit() {
        return profit;
    }

    private void updateProfit() {
        this.profit = this.revenue - this.cost;
    }
}
