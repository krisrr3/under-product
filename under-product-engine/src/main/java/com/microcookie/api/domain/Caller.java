/**
 * Interface that represents the caller of an API.
*/


 package com.microcookie.api.domain;


public class Caller {
    private String id;
    private String name;
    private String type; // e.g WebApp, MobileApp, API



    // Constructors
    public Caller() {}

    public Caller(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

        // Getter and Setter for type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

