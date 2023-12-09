package org.example.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Company {

    @JsonProperty
    private String name;
    @JsonProperty
    private double earnings;

    public Company() {
    }

    public Company(String name, double earnings) {
        this.name = name;
        this.earnings = earnings;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getEarnings() {
        return earnings;
    }


}
