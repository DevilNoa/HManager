package org.example.services;

import java.sql.SQLException;
import java.util.List;
import org.example.core.Building;
import org.example.dao.BuildingDAO;

public class BuildingServices {
  BuildingDAO buildingDAO;

  // Constructor to initialize BuildingServices with BuildingDAO instance
  public BuildingServices(BuildingDAO buildingDAO) {
    this.buildingDAO = buildingDAO;
  }

  // Method to create a building
  public void addBuilding(Building newBuilding) throws SQLException {
    buildingDAO.createBuilding(
        newBuilding.getBuildingName(),
        newBuilding.getCompanyName(),
        newBuilding.getBuildingAddress(),
        newBuilding.getBuildingFloors(),
        newBuilding.getBuildingFlats(),
        newBuilding.getBuildingSqft());
  }

  // Method to get a building by name
  public Building getBuildingByName(String name) throws SQLException {
    return buildingDAO.getBuildingByName(name);
  }

  // Method to get all buildings
  public List<Building> getAllBuildings() throws SQLException {
    return buildingDAO.getAllBuildings();
  }

  // Method to update a building
  public Building updateBuilding(String name, Building newBuilding) throws SQLException {
    return buildingDAO.updateBuilding(name, newBuilding);
  }

  // Method to delete a building
  public boolean deleteBuilding(String name) throws SQLException {
    return buildingDAO.deleteBuilding(name);
  }
}
