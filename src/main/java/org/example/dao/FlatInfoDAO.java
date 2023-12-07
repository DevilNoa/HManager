package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.core.FlatInfo;

public class FlatInfoDAO {
  Connection connection;

  public FlatInfoDAO(Connection connection) {
    this.connection = connection;
  }

  // Method to create a flat info
//todo :fix this or make it again
  public void createFlatInfo(FlatInfo flatInfo) throws SQLException {
    try {
      String sql =
          "INSERT INTO flat_info (building_name, flat_number, flat_floor, flat_elevator, flat_sqft, flat_people, flat_kids, flat_pets, flat_pets_elevator) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, flatInfo.getBuildingName());
      statement.setInt(2, flatInfo.getFlatNumber());
      statement.setInt(3, flatInfo.getFlatFloor());
      statement.setBoolean(4, flatInfo.isFlatElevator());
      statement.setFloat(5, flatInfo.getFlatSqft());
      statement.setInt(6, flatInfo.getFlatPeople());
      statement.setInt(7, flatInfo.getFlatKids());
      statement.setBoolean(8, flatInfo.isFlatPets());
      statement.setBoolean(9, flatInfo.isFlatPetsElevator());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new SQLException("Error in creating flat info", e);
    }
  }

  // Method to get a flat info by building name and flat number
  public FlatInfo getFlatInfo(String buildingName, int flatNumber) throws SQLException {
    try {
      String sql = "SELECT * FROM flat_info WHERE building_name = ? AND flat_number = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, buildingName);
      statement.setInt(2, flatNumber);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        int flatFloor = resultSet.getInt("flat_floor");
        boolean flatElevator = resultSet.getBoolean("flat_elevator");
        float flatSqft = resultSet.getFloat("flat_sqft");
        int flatPeople = resultSet.getInt("flat_people");
        int flatKids = resultSet.getInt("flat_kids");
        boolean flatPets = resultSet.getBoolean("flat_pets");
        boolean flatPetsElevator = resultSet.getBoolean("flat_pets_elevator");

        return new FlatInfo(
            buildingName,
            flatNumber,
            flatFloor,
            flatElevator,
            flatSqft,
            flatPeople,
            flatKids,
            flatPets,
            flatPetsElevator);
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw new SQLException("Error in getting flat info", e);
    }
  }

  // Method to get all flat info
  public List<FlatInfo> getAllFlatInfoForBuilding(String buildingName) throws SQLException {
    List<FlatInfo> flatInfos = new ArrayList<>();

    try {
      String sql = "SELECT * FROM flat_info WHERE building_name = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, buildingName);

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int flatNumber = resultSet.getInt("flat_number");
        int flatFloor = resultSet.getInt("flat_floor");
        boolean flatElevator = resultSet.getBoolean("flat_elevator");
        float flatSqft = resultSet.getFloat("flat_sqft");
        int flatPeople = resultSet.getInt("flat_people");
        int flatKids = resultSet.getInt("flat_kids");
        boolean flatPets = resultSet.getBoolean("flat_pets");
        boolean flatPetsElevator = resultSet.getBoolean("flat_pets_elevator");

        FlatInfo flatInfo =
            new FlatInfo(
                buildingName,
                flatNumber,
                flatFloor,
                flatElevator,
                flatSqft,
                flatPeople,
                flatKids,
                flatPets,
                flatPetsElevator);
        flatInfos.add(flatInfo);
      }
    } catch (SQLException e) {
      throw new SQLException("Error in getting flat info", e);
    }

    return flatInfos;
  }

  // Method to update a flat info
  public FlatInfo updateFlatInfo(String buildingName, int flatNumber, FlatInfo flatInfo)
      throws SQLException {
    try {
      String sql =
          "UPDATE flat_info SET flat_floor = ?, flat_elevator = ?, flat_sqft = ?, flat_people = ?, flat_kids = ?, flat_pets = ?, flat_pets_elevator = ? "
              + "WHERE building_name = ? AND flat_number = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, flatInfo.getFlatFloor());
      statement.setBoolean(2, flatInfo.isFlatElevator());
      statement.setFloat(3, flatInfo.getFlatSqft());
      statement.setInt(4, flatInfo.getFlatPeople());
      statement.setInt(5, flatInfo.getFlatKids());
      statement.setBoolean(6, flatInfo.isFlatPets());
      statement.setBoolean(7, flatInfo.isFlatPetsElevator());
      statement.setString(8, flatInfo.getBuildingName());
      statement.setInt(9, flatInfo.getFlatNumber());

      statement.executeUpdate();
    } catch (SQLException e) {
      throw new SQLException("Error in updating flat info", e);
    }
    return flatInfo;
  }

  // Method to delete a flat info
  public boolean deleteFlatInfo(String buildingName, int flatNumber) throws SQLException {
    try {
      String sql = "DELETE FROM flat_info WHERE building_name = ? AND flat_number = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, buildingName);
      statement.setInt(2, flatNumber);

      int affectedRows = statement.executeUpdate();

      return affectedRows > 0;
    } catch (SQLException e) {
      throw new SQLException("Error in deleting flat info", e);
    }
  }
}
