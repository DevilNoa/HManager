package org.example.core;

public class FlatInfo {
    private String buildingName;
    private int flatNumber;
    private int flatFloor;
    private boolean flatElevator;
    private float flatSqft;
    private int flatPeople;
    private int flatKids;
    private boolean flatPets;
    private boolean flatPetsElevator;

    public FlatInfo(String buildingName, int flatNumber, int flatFloor, boolean flatElevator, float flatSqft, int flatPeople, int flatKids, boolean flatPets, boolean flatPetsElevator) {
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

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(int flatNumber) {
        this.flatNumber = flatNumber;
    }

    public int getFlatFloor() {
        return flatFloor;
    }

    public void setFlatFloor(int flatFloor) {
        this.flatFloor = flatFloor;
    }

    public boolean isFlatElevator() {
        return flatElevator;
    }

    public void setFlatElevator(boolean flatElevator) {
        this.flatElevator = flatElevator;
    }

    public float getFlatSqft() {
        return flatSqft;
    }

    public void setFlatSqft(float flatSqft) {
        this.flatSqft = flatSqft;
    }

    public int getFlatPeople() {
        return flatPeople;
    }

    public void setFlatPeople(int flatPeople) {
        this.flatPeople = flatPeople;
    }

    public int getFlatKids() {
        return flatKids;
    }

    public void setFlatKids(int flatKids) {
        this.flatKids = flatKids;
    }

    public boolean isFlatPets() {
        return flatPets;
    }

    public void setFlatPets(boolean flatPets) {
        this.flatPets = flatPets;
    }

    public boolean isFlatPetsElevator() {
        return flatPetsElevator;
    }

    public void setFlatPetsElevator(boolean flatPetsElevator) {
        this.flatPetsElevator = flatPetsElevator;
    }
}
