/**
 * APIs are the systems that are at the back of the channels or higher level systems to fulfill a process, such as a payment.
 * 
*/
package com.microcookie.api.domain;

public class ApiCall {
    private String id;
    private String apiName;
    private Caller calledFrom;
    private String calledLayer;
    private String originatingProduct;


    // Constructors, getters, and setters
    public ApiCall() {
    }

    public ApiCall(String id, String apiName, Caller calledFrom, String originatingProduct) {
        this.id = id;
        this.apiName = apiName;
        this.calledFrom = calledFrom;
        this.originatingProduct = originatingProduct;
    }

    public String getCalledLayer() {
        return calledLayer;
    }

    public void setCalledLayer(String calledLayer) {
        this.calledLayer = calledLayer;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Caller getCalledFrom() {
        return calledFrom;
    }

    public void setCalledFrom(Caller calledFrom) {
        this.calledFrom = calledFrom;
    }

    public String getOriginatingProduct() {
        return originatingProduct;
    }

    public void setOriginatingProduct(String originatingProduct) {
        this.originatingProduct = originatingProduct;
    }

    @Override
    public String toString() {
        return "ApiCall{id='" + id + "', apiName='" + apiName + "', calledFrom=" + calledFrom + ", calledLayer='" + calledLayer + "', originatingProduct='" + originatingProduct + "'}";
    }

}
