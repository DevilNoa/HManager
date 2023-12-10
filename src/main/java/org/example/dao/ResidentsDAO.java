package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.core.Residents;

public class ResidentsDAO {
  Connection connection;

  public ResidentsDAO(Connection connection) {
    this.connection = connection;
  }

  // Get all residents
  public List<Residents> getAllResidents() throws SQLException {
    List<Residents> residents = new ArrayList<>();

    try {
      String sql = "select * from residents";
      PreparedStatement statement = connection.prepareStatement(sql);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int residentId = resultSet.getInt("resident_id");
        String residentName = resultSet.getString("resident_name");
        int residentAge = resultSet.getInt("resident_age");
        int residentFlat = resultSet.getInt("resident_flat");
        String residentBuilding = resultSet.getString("resident_building");

        Residents resident =
            new Residents(residentId, residentName, residentAge, residentFlat, residentBuilding);

        residents.add(resident);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in getting residents info", e);
    }
    return residents;
  }

  // Get residents by flat
  public List<Residents> getResidentsByFlat(int flat) throws SQLException {
    List<Residents> residents = new ArrayList<>();

    try {
      String sql = "select * from residents where resident_flat = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, flat);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int residentId = resultSet.getInt("resident_id");
        String residentName = resultSet.getString("resident_name");
        int residentAge = resultSet.getInt("resident_age");
        int residentFlat = resultSet.getInt("resident_flat");
        String residentBuilding = resultSet.getString("resident_building");

        Residents resident =
            new Residents(residentId, residentName, residentAge, residentFlat, residentBuilding);

        residents.add(resident);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in getting residents info", e);
    }
    return residents;
  }

  // Get residents by building
  public List<Residents> getResidentsByBuilding(String building) throws SQLException {
    List<Residents> residents = new ArrayList<>();

    try {
      String sql = "select * from residents where resident_building = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, building);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int residentId = resultSet.getInt("resident_id");
        String residentName = resultSet.getString("resident_name");
        int residentAge = resultSet.getInt("resident_age");
        int residentFlat = resultSet.getInt("resident_flat");
        String residentBuilding = resultSet.getString("resident_building");

        Residents resident =
            new Residents(residentId, residentName, residentAge, residentFlat, residentBuilding);

        residents.add(resident);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in getting residents info", e);
    }
    return residents;
  }

  // Get residents by building and flat
  public List<Residents> getResidentsByBuildingAndFlat(String building, int flat)
      throws SQLException {
    List<Residents> residents = new ArrayList<>();

    try {
      String sql = "select * from residents where resident_building = ? and resident_flat = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, building);
      statement.setInt(2, flat);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int residentId = resultSet.getInt("resident_id");
        String residentName = resultSet.getString("resident_name");
        int residentAge = resultSet.getInt("resident_age");
        int residentFlat = resultSet.getInt("resident_flat");
        String residentBuilding = resultSet.getString("resident_building");

        Residents resident =
            new Residents(residentId, residentName, residentAge, residentFlat, residentBuilding);

        residents.add(resident);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in getting residents info", e);
    }
    return residents;
  }

  // Get residents by building and flat and name
  public List<Residents> getResidentsByBuildingAndFlatAndName(
      String building, int flat, String name) throws SQLException {
    List<Residents> residents = new ArrayList<>();

    try {
      String sql =
          "select * from residents where resident_building = ? and resident_flat = ? and resident_name = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, building);
      statement.setInt(2, flat);
      statement.setString(3, name);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int residentId = resultSet.getInt("resident_id");
        String residentName = resultSet.getString("resident_name");
        int residentAge = resultSet.getInt("resident_age");
        int residentFlat = resultSet.getInt("resident_flat");
        String residentBuilding = resultSet.getString("resident_building");

        Residents resident =
            new Residents(residentId, residentName, residentAge, residentFlat, residentBuilding);

        residents.add(resident);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in getting residents info", e);
    }
    return residents;
  }

  // Get residents by name
  public List<Residents> getResidentsByName(String name) throws SQLException {
    List<Residents> residents = new ArrayList<>();

    try {
      String sql = "select * from residents where resident_name = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, name);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int residentId = resultSet.getInt("resident_id");
        String residentName = resultSet.getString("resident_name");
        int residentAge = resultSet.getInt("resident_age");
        int residentFlat = resultSet.getInt("resident_flat");
        String residentBuilding = resultSet.getString("resident_building");

        Residents resident =
            new Residents(residentId, residentName, residentAge, residentFlat, residentBuilding);

        residents.add(resident);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in getting residents info", e);
    }
    return residents;
  }

  // Create resident
  public void createResident(Residents resident) throws SQLException {
    try {
      String sql =
          "insert into residents (resident_name, resident_age, resident_flat, resident_building) values (?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, resident.getName());
      statement.setInt(2, resident.getAge());
      statement.setInt(3, resident.getFlat());
      statement.setString(4, resident.getBuilding());

      int affectedRows = statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in creating resident", e);
    }
  }

  // Update resident
  public void updateResident(Residents updatedResident) throws SQLException {
    try {
      String sql =
          "update residents set resident_name = ?, resident_age = ?, resident_flat = ?, resident_building = ? where resident_id = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, updatedResident.getName());
      statement.setInt(2, updatedResident.getAge());
      statement.setInt(3, updatedResident.getFlat());
      statement.setString(4, updatedResident.getBuilding());
      statement.setInt(5, updatedResident.getId());

      int affectedRows = statement.executeUpdate();

      if (affectedRows > 0) {
        System.out.println("Resident updated successfully!");
      } else {
        System.out.println("Resident not found for update.");
      }
    } catch (SQLException e) {
      e.printStackTrace(); // Print the stack trace
      throw new SQLException("Error in updating resident", e);
    }
  }

  // Delete resident
  public void deleteResident(int id) throws SQLException {
    try {
      String sql = "delete from residents where resident_id = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, id);

      int affectedRows = statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in deleting resident", e);
    }
  }
}
