package org.example.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlatInfo {
  @JsonProperty private String buildingName;
  @JsonProperty private int flatNumber;
  @JsonProperty private int flatFloor;
  @JsonProperty private boolean flatElevator;
  @JsonProperty private float flatSqft;
  @JsonProperty private int flatPeople;
  @JsonProperty private int flatKids;
  @JsonProperty private boolean flatPets;
  @JsonProperty private boolean flatPetsElevator;
  @JsonProperty private int flatId;

  public FlatInfo() {}

  public FlatInfo(
      String buildingName,
      int flatNumber,
      int flatFloor,
      boolean flatElevator,
      float flatSqft,
      int flatPeople,
      int flatKids,
      boolean flatPets,
      boolean flatPetsElevator,
      int flatId) {
    this.buildingName = buildingName;
    this.flatNumber = flatNumber;
    this.flatFloor = flatFloor;
    this.flatElevator = flatElevator;
    this.flatSqft = flatSqft;
    this.flatPeople = flatPeople;
    this.flatKids = flatKids;
    this.flatPets = flatPets;
    this.flatPetsElevator = flatPetsElevator;
    this.flatId = flatId;
  }
  public FlatInfo(
          String buildingName,
          int flatNumber,
          int flatFloor,
          boolean flatElevator,
          float flatSqft,
          int flatPeople,
          int flatKids,
          boolean flatPets,
          boolean flatPetsElevator) {
    this.buildingName = buildingName;
    this.flatNumber = flatNumber;
    this.flatFloor = flatFloor;
    this.flatElevator = flatElevator;
    this.flatSqft = flatSqft;
    this.flatPeople = flatPeople;
    this.flatKids = flatKids;
    this.flatPets = flatPets;
    this.flatPetsElevator = flatPetsElevator;
  }

  public String getBuildingName() {
    return buildingName;
  }

  public int getFlatNumber() {
    return flatNumber;
  }

  public int getFlatFloor() {
    return flatFloor;
  }

  public boolean isFlatElevator() {
    return flatElevator;
  }

  public float getFlatSqft() {
    return flatSqft;
  }

  public int getFlatPeople() {
    return flatPeople;
  }

  public int getFlatKids() {
    return flatKids;
  }

  public boolean isFlatPets() {
    return flatPets;
  }

  public boolean isFlatPetsElevator() {
    return flatPetsElevator;
  }

}