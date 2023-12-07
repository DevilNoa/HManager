package org.example.core;

public class Employ {

    private int id;
    private String name;
    private String companyName;
    private String buildingAddress;

    public Employ(int id, String name, String companyName, String buildingAddress) {
        this.id = id;
        this.name = name;
        this.companyName = companyName;
        this.buildingAddress = buildingAddress;
    }

    public Employ() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }
}
