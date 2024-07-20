package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.core.FlatInfo;

public class FlatDAO {

  Connection connection;

  public FlatDAO(Connection connection) {
    this.connection = connection;
  }

  // Get all Flats
  public List<FlatInfo> getAllFlats() throws SQLException {
    List<FlatInfo> flatInfo = new ArrayList<>();
    try {
      String sql = "select * from flat_info";
      PreparedStatement statement = connection.prepareStatement(sql);

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int flatId = resultSet.getInt("flat_id");
        String buildingName = resultSet.getString("building_name");
        int flatFloor = resultSet.getInt("flat_floor");
        int flatNumber = resultSet.getInt("flat_number");
        boolean flatElevator = resultSet.getBoolean("flat_elevator");
        float flatSqft = resultSet.getFloat("flat_sqm");
        int flatPeople = resultSet.getInt("flat_people");
        int flatKids = resultSet.getInt("flat_kids");
        boolean flatPets = resultSet.getBoolean("flat_pet");
        boolean flatPetsElevator = resultSet.getBoolean("flat_pet_elevator");

        flatInfo.add(
            new FlatInfo(
                buildingName,
                flatNumber,
                flatFloor,
                flatElevator,
                flatSqft,
                flatPeople,
                flatKids,
                flatPets,
                flatPetsElevator,
                flatId));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in getting flat info", e);
    }
    return flatInfo;
  }

  // Get all Flats by building
  public List<FlatInfo> getAllFlatsByBuilding(String buildingName) throws SQLException {
    List<FlatInfo> flatInfo = new ArrayList<>();
    try {
      String sql = "SELECT * FROM flat_info WHERE building_name = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, buildingName);

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int flatId = resultSet.getInt("flat_id");
        String building = resultSet.getString("building_name");
        int flatFloor = resultSet.getInt("flat_floor");
        int flatNumber = resultSet.getInt("flat_number");
        boolean flatElevator = resultSet.getBoolean("flat_elevator");
        float flatSqft = resultSet.getFloat("flat_sqm");
        int flatPeople = resultSet.getInt("flat_people");
        int flatKids = resultSet.getInt("flat_kids");
        boolean flatPets = resultSet.getBoolean("flat_pet");
        boolean flatPetsElevator = resultSet.getBoolean("flat_pet_elevator");

        flatInfo.add(
            new FlatInfo(
                building,
                flatNumber,
                flatFloor,
                flatElevator,
                flatSqft,
                flatPeople,
                flatKids,
                flatPets,
                flatPetsElevator,
                flatId));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in getting flat info by building", e);
    }
    return flatInfo;
  }

  // Get all Flats by building and apartment number
  public List<FlatInfo> getAllFlatsByBuildingAndFlatNum(String buildingName, int flatNumber)
      throws SQLException {
    List<FlatInfo> flatInfo = new ArrayList<>();
    try {
      String sql = "SELECT * FROM flat_info WHERE building_name = ? AND flat_number = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, buildingName);
      statement.setInt(2, flatNumber);

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int flatId = resultSet.getInt("flat_id");
        String building = resultSet.getString("building_name");
        int flatFloor = resultSet.getInt("flat_floor");
        boolean flatElevator = resultSet.getBoolean("flat_elevator");
        float flatSqft = resultSet.getFloat("flat_sqm");
        int flatPeople = resultSet.getInt("flat_people");
        int flatKids = resultSet.getInt("flat_kids");
        boolean flatPets = resultSet.getBoolean("flat_pet");
        boolean flatPetsElevator = resultSet.getBoolean("flat_pet_elevator");

        flatInfo.add(
            new FlatInfo(
                building,
                flatNumber,
                flatFloor,
                flatElevator,
                flatSqft,
                flatPeople,
                flatKids,
                flatPets,
                flatPetsElevator,
                flatId));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in getting flat info by building", e);
    }
    return flatInfo;
  }

  // Create a new Flat
  public void createFlat(FlatInfo flatInfo) throws SQLException {
    try {
      String sql =
          "INSERT INTO flat_info (building_name, flat_floor, flat_number, flat_elevator, flat_sqm, flat_people, flat_kids, flat_pet, flat_pet_elevator) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, flatInfo.getBuildingName());
      statement.setInt(2, flatInfo.getFlatFloor());
      statement.setInt(3, flatInfo.getFlatNumber());
      statement.setBoolean(4, flatInfo.isFlatElevator());
      statement.setFloat(5, flatInfo.getFlatSqft());
      statement.setInt(6, flatInfo.getFlatPeople());
      statement.setInt(7, flatInfo.getFlatKids());
      statement.setBoolean(8, flatInfo.isFlatPets());
      statement.setBoolean(9, flatInfo.isFlatPetsElevator());

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in creating flat info", e);
    }
  }

  // Update a Flat
  public FlatInfo updateFlat(String currentBuildingName, int currentFlatNumber, FlatInfo updatedFlat)
          throws SQLException {
    try {
      String sql =
              "UPDATE flat_info SET building_name=?, flat_floor=?, flat_elevator=?, flat_sqm=?, flat_people=?, flat_kids=?, flat_pet=?, flat_pet_elevator=? "
                      + "WHERE building_name=? AND flat_number=?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, updatedFlat.getBuildingName());
      statement.setInt(2, updatedFlat.getFlatFloor());
      statement.setBoolean(3, updatedFlat.isFlatElevator());
      statement.setFloat(4, updatedFlat.getFlatSqft());
      statement.setInt(5, updatedFlat.getFlatPeople());
      statement.setInt(6, updatedFlat.getFlatKids());
      statement.setBoolean(7, updatedFlat.isFlatPets());
      statement.setBoolean(8, updatedFlat.isFlatPetsElevator());
      statement.setString(9, currentBuildingName);
      statement.setInt(10, currentFlatNumber);
      statement.executeUpdate();

      String sql2 = "UPDATE flat_info SET flat_number=? WHERE building_name=? AND flat_number=?";
      PreparedStatement statement2 = connection.prepareStatement(sql2);
      statement2.setInt(1, updatedFlat.getFlatNumber()); // Set the new flat number
      statement2.setString(2, updatedFlat.getBuildingName());
      statement2.setInt(3, currentFlatNumber);
      int rowsUpdated = statement2.executeUpdate();

      if (rowsUpdated > 0) {
        return updatedFlatHelper(updatedFlat.getBuildingName(), updatedFlat.getFlatNumber());
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in updating flat", e);
    }
  }

  public FlatInfo updatedFlatHelper(String buildingName, int flatNumber) throws SQLException {
    String sql = "SELECT * FROM flat_info WHERE building_name = ? AND flat_number = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, buildingName);
      statement.setInt(2, flatNumber);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          int flatFloor = resultSet.getInt("flat_floor");
          boolean flatElevator = resultSet.getBoolean("flat_elevator");
          float flatSqft = resultSet.getFloat("flat_sqm");
          int flatPeople = resultSet.getInt("flat_people");
          int flatKids = resultSet.getInt("flat_kids");
          boolean flatPets = resultSet.getBoolean("flat_pet");
          boolean flatPetsElevator = resultSet.getBoolean("flat_pet_elevator");

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
        }
      }
    }
    return null;
  }

  // Delete a Flat
  public boolean deleteFlat(String buildingName, int flatNumber) throws SQLException {
    try {
      String sql = "DELETE FROM flat_info WHERE building_name = ? AND flat_number = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, buildingName);
      statement.setInt(2, flatNumber);
      int rowsDeleted = statement.executeUpdate();
      System.out.println("Rows deleted: " + rowsDeleted);
      return rowsDeleted > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in deleting flat", e);
    }
  }
}
