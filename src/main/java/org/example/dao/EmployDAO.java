package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.core.Employ;

public class EmployDAO {
  Connection connection;

  public EmployDAO(Connection connection) {
    this.connection = connection;
  }

  // Method to create an employ
  public void createEmploy(Employ employ) throws SQLException {
    try {
      String sql =
          "INSERT INTO employ (employ_name, company_name, building_address) VALUES (?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, employ.getName());
      statement.setString(2, employ.getCompanyName());
      statement.setString(3, employ.getBuildingAddress());
      statement.executeUpdate(); // Execute the query
    } catch (SQLException e) {
      throw new SQLException("Error in creating employ", e);
    }
  }

  // Method to get an employ by id
  public Employ getEmployByID(int id) throws SQLException {
    try {
      String sql = "SELECT * FROM employ WHERE employ_id = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, id);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        id = resultSet.getInt("employ_id");
        String companyName = resultSet.getString("company_name");
        String buildingAddress = resultSet.getString("building_address");
        String name = resultSet.getString("employ_name");

        return new Employ(id, name, companyName, buildingAddress);
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw new SQLException("Error in getting employ", e);
    }
  }

  // Method to get all employs
  public List<Employ> getAllEmploys() throws SQLException {
    List<Employ> employs = new ArrayList<>();

    try {
      String sql = "SELECT * FROM employ";
      PreparedStatement statement = connection.prepareStatement(sql);

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int id = resultSet.getInt("employ_id");
        String employName = resultSet.getString("employ_name");
        String companyName = resultSet.getString("company_name");
        String buildingAddress = resultSet.getString("building_address");

        employs.add(new Employ(id, employName, companyName, buildingAddress));
      }
    } catch (SQLException e) {
      throw new SQLException("Error in getting all employs", e);
    }

    return employs;
  }

  public Employ updateEmploy(int id, Employ updatedEmploy) throws SQLException {
    try {
      // Check if company exists
      String checkCompanySql = "SELECT 1 FROM company WHERE company_name = ?";
      PreparedStatement checkCompanyStmt = connection.prepareStatement(checkCompanySql);
      checkCompanyStmt.setString(1, updatedEmploy.getCompanyName());
      ResultSet rs = checkCompanyStmt.executeQuery();
      if (!rs.next()) {
        throw new SQLException("Company does not exist");
      }

      String sql =
              "UPDATE employ SET employ_name = ?, company_name = ?, building_address = ? WHERE employ_id = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, updatedEmploy.getName());
      statement.setString(2, updatedEmploy.getCompanyName());
      statement.setString(3, updatedEmploy.getBuildingAddress());
      statement.setInt(4, id);
      statement.executeUpdate();
      return updatedEmploy;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error in updating employ", e);
    }
  }

  // Method to delete an employ by name
  public boolean deleteEmploy(int id) throws SQLException {
    try {
      String sql = "DELETE FROM employ WHERE employ_id = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, id);
      int rowsDeleted = statement.executeUpdate();
      return rowsDeleted > 0;
    } catch (SQLException e) {
      throw new SQLException("Error in deleting employ", e);
    }
  }
}
