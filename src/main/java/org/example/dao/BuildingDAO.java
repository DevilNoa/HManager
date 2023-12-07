package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.core.Building;

public class BuildingDAO {
  Connection connection;

  public BuildingDAO(Connection connection) {
    this.connection = connection;
  }

  public void createBuilding(
          String name,
          String companyName,
          String address,
          int floors,
          int flats,
          float squareCommonPart) throws SQLException {
    try {
      String sql =
              "INSERT INTO building (building_name, company_name, building_address, building_floors, building_flats, building_square_common_part) "
                      + "VALUES (?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, name);
      statement.setString(2, companyName);
      statement.setString(3, address);
      statement.setInt(4, floors);
      statement.setInt(5, flats);
      statement.setFloat(6, squareCommonPart);
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new SQLException("Error in creating building", e.getMessage(),e);
//      System.out.println("Error in creating building: " + e.getMessage());
//      e.printStackTrace();
    }
  }

  // Read a building by name (get a row from the 'building' table)
  public Building getBuildingByName(String name) throws SQLException {
    try {
      String sql = "SELECT * FROM building WHERE building_name = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, name);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        String companyName = resultSet.getString("company_name");
        String address = resultSet.getString("building_address");
        int floors = resultSet.getInt("building_floors");
        int flats = resultSet.getInt("building_flats");
        float squareCommonPart = resultSet.getFloat("building_square_common_part");

        return new Building(name, companyName, address, floors, flats, squareCommonPart);
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw new SQLException("Error in getting building",e.getMessage());
//      e.printStackTrace();
    }
  }

  // Read all buildings (get all rows from the 'building' table)
  public List<Building> getAllBuildings() throws SQLException {
    List<Building> buildings = new ArrayList<>(); // Create a list to hold the buildings

    try {
      String sql = "SELECT * FROM building";
      PreparedStatement statement = connection.prepareStatement(sql);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        String name = resultSet.getString("building_name");
        String companyName = resultSet.getString("company_name");
        String address = resultSet.getString("building_address");
        int floors = resultSet.getInt("building_floors");
        int flats = resultSet.getInt("building_flats");
        float squareCommonPart = resultSet.getFloat("building_square_common_part");

        Building building =
            new Building(name, companyName, address, floors, flats, squareCommonPart);
        buildings.add(building); // Add the building to the list
      }
    } catch (SQLException e) {
      throw new SQLException("Error in getting buildings", e);
    }

    return buildings;
  }

  // Update a building (update a row in the 'building' table)
  public void updateBuilding(Building building) throws SQLException {
    try {
      String sql =
              "UPDATE building SET company_name = ?, building_address = ?, building_floors = ?, "
                      + "building_flats = ?, building_square_common_part = ? WHERE building_name = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, building.getCompanyName());
      statement.setString(2, building.getBuildingAddress());
      statement.setInt(3, building.getBuildingFloors());
      statement.setInt(4, building.getBuildingFlats());
      statement.setFloat(5, building.getBuildingSqft());
      statement.setString(6, building.getBuildingName());

      int affectedRows = statement.executeUpdate();

      if (affectedRows > 0) {
        new Building(
            building.getBuildingName(),
            building.getCompanyName(),
            building.getBuildingAddress(),
            building.getBuildingFloors(),
            building.getBuildingFlats(),
            building.getBuildingSqft());
      }
    } catch (SQLException e) {
      throw new SQLException("Error in updating building", e);
    }
  }

  // Delete a building (delete a row from the 'building' table)
  public boolean deleteBuilding(String name) throws SQLException {
    try {
      String sql = "DELETE FROM building WHERE building_name = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, name);

      int affectedRows = statement.executeUpdate();

      return affectedRows > 0;
    } catch (SQLException e) {
      throw new SQLException("Error in deleting building", e);
    }
  }
}
