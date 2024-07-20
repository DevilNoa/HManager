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
      float squareCommonPart)
      throws SQLException {
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
      throw new SQLException("Error in creating building", e.getMessage(), e);
      //      System.out.println("Error in creating building: " + e.getMessage());
      //      e.printStackTrace();
    }
  }

  // Get a building by name
  public Building getBuildingByName(String name) throws SQLException {
    try {
      String sql = "SELECT * FROM building WHERE building_name = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, name);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        int buildingId = resultSet.getInt("building_id");
        String companyName = resultSet.getString("company_name");
        String address = resultSet.getString("building_address");
        int floors = resultSet.getInt("building_floors");
        int flats = resultSet.getInt("building_flats");
        float squareCommonPart = resultSet.getFloat("building_square_common_part");

        return new Building(buildingId, name, companyName, address, floors, flats, squareCommonPart);
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw new SQLException("Error in getting building", e.getMessage());
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
        int buildingId = resultSet.getInt("building_id");
        String name = resultSet.getString("building_name");
        String companyName = resultSet.getString("company_name");
        String address = resultSet.getString("building_address");
        int floors = resultSet.getInt("building_floors");
        int flats = resultSet.getInt("building_flats");
        float squareCommonPart = resultSet.getFloat("building_square_common_part");

        Building building =
            new Building(buildingId, name, companyName, address, floors, flats, squareCommonPart);
        buildings.add(building);
      }
    } catch (SQLException e) {
      throw new SQLException("Error in getting buildings", e);
    }

    return buildings;
  }

  // Update a building
  public Building updateBuilding(String name, Building newBuilding) throws SQLException {
    try {
      String checkSql = "SELECT * FROM building WHERE building_name = ?";
      PreparedStatement checkStatement = connection.prepareStatement(checkSql);
      checkStatement.setString(1, name);
      ResultSet checkResultSet = checkStatement.executeQuery();

      if (!checkResultSet.next()) {
        System.out.println("Building not found for update.");
        return null;
      }

      String sql =
          "UPDATE building SET building_name = ?, company_name = ?, building_address = ?, building_floors = ?, "
              + "building_flats = ?, building_square_common_part = ? WHERE building_name = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, newBuilding.getBuildingName());
      statement.setString(2, newBuilding.getCompanyName());
      statement.setString(3, newBuilding.getBuildingAddress());
      statement.setInt(4, newBuilding.getBuildingFloors());
      statement.setInt(5, newBuilding.getBuildingFlats());
      statement.setFloat(6, newBuilding.getBuildingSqft());
      statement.setString(7, name);

      int affectedRows = statement.executeUpdate();

      if (affectedRows > 0) {
        System.out.println("Building updated successfully!");
        return newBuilding;
      } else {
        System.out.println("Building not found for update.");
        return null;
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
