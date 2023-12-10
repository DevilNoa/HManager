package org.example.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Residents {
  @JsonProperty int age;

  @JsonProperty private int flat;

  @JsonProperty private String name;
  @JsonProperty private String building;
  @JsonProperty private int id;

  public Residents() {}

  public Residents(int id, String name, int age, int flat, String building) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.flat = flat;
    this.building = building;
  }

  public int getAge() {
    return age;
  }

  public int getFlat() {
    return flat;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
