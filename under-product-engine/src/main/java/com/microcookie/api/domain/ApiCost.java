package com.microcookie.api.domain;

public class ApiCost {
    private String id;
    private double tco;
    private double costPerUnitCall;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
