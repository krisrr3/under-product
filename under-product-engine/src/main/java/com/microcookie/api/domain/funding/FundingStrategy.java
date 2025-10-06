package com.microcookie.api.domain.funding;


import com.microcookie.api.domain.ApiCall;
import com.microcookie.api.domain.BusinessDomain;

public class FundingStrategy {

  
    private String apiId;
    private String decision ;
    private String fundingStrategyType;
    private int amount ;
    private String decisionDate;

    public BusinessDomain whoPays(ApiCall apiCall){
        return new BusinessDomain();
    }
    public String getApiId() {
        return apiId;
    }
    public void setApiId(String apiId) {
        this.apiId = apiId;
    }
    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getFundingStrategyType() {
        return fundingStrategyType;
    }

    public void setFundingStrategyType(String fundingStrategyType) {
        this.fundingStrategyType = fundingStrategyType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(String decisionDate) {
        this.decisionDate = decisionDate;
    }

}
