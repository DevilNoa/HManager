package org.example.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employ {

  private int id;
  @JsonProperty private String name;
  @JsonProperty private String companyName;
  @JsonProperty private String buildingAddress;

  public Employ(int id, String name, String companyName, String buildingAddress) {
    this.id = id;
    this.name = name;
    this.companyName = companyName;
    this.buildingAddress = buildingAddress;
  }

  public Employ() {}

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

  public String getBuildingAddress() {
    return buildingAddress;
  }
}
