package org.example.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Building {
  @JsonProperty private String buildingName;
  @JsonProperty private String companyName;
  @JsonProperty private String buildingAddress;

  @JsonProperty private int buildingFloors;
  @JsonProperty private int buildingFlats;
  @JsonProperty private float buildingSqft;

  public String getBuildingName() {
    return buildingName;
  }

  public Building(
      String buildingName,
      String companyName,
      String buildingAddress,
      int buildingFloors,
      int buildingFlats,
      float buildingSqft) {
    this.buildingName = buildingName;
    this.companyName = companyName;
    this.buildingAddress = buildingAddress;
    this.buildingFloors = buildingFloors;
    this.buildingFlats = buildingFlats;
    this.buildingSqft = buildingSqft;
  }

  public Building() {}

  public String getCompanyName() {
    return companyName;
  }

  public String getBuildingAddress() {
    return buildingAddress;
  }

  public int getBuildingFloors() {
    return buildingFloors;
  }

  public int getBuildingFlats() {
    return buildingFlats;
  }

  public float getBuildingSqft() {
    return buildingSqft;
  }
}
