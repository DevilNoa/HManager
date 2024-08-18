package org.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.core.Fees;

public class FeesDAO {
  private final Connection connection;

  public FeesDAO(Connection connection) {
    this.connection = connection;
  }

  // Method to create a fee
  public void createFee(Fees fee) throws SQLException {
    String sql = "INSERT INTO fees (fee_name, fee_description, fee_amount, fee_due_date, fee_type, building_id, flat_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, fee.getFeeName());
      statement.setString(2, fee.getFeeDescription());
      statement.setDouble(3, fee.getFeeAmount());
      statement.setDate(4, new java.sql.Date(fee.getFeeDueDate().getTime()));
      statement.setString(5, fee.getFeeType());
      statement.setObject(6, fee.getBuildingId(), Types.INTEGER);
      statement.setObject(7, fee.getFlatId(), Types.INTEGER);
      statement.executeUpdate();
    }
  }

  // Method to get a fee by ID
  public Fees getFeeById(int feeId) throws SQLException {
    String sql = "SELECT * FROM fees WHERE fee_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, feeId);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return new Fees(
          resultSet.getInt("fee_id"),
          resultSet.getString("fee_name"),
          resultSet.getString("fee_description"),
          resultSet.getDouble("fee_amount"),
          resultSet.getDate("fee_due_date"),
          resultSet.getString("fee_type"),
          (Integer) resultSet.getObject("building_id"),
          (Integer) resultSet.getObject("flat_id")
        );
      } else {
        return null;
      }
    }
  }

  // Method to get all fees
  public List<Fees> getAllFees() throws SQLException {
    List<Fees> feesList = new ArrayList<>();
    String sql = "SELECT * FROM fees";
    try (PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        feesList.add(new Fees(
          resultSet.getInt("fee_id"),
          resultSet.getString("fee_name"),
          resultSet.getString("fee_description"),
          resultSet.getDouble("fee_amount"),
          resultSet.getDate("fee_due_date"),
          resultSet.getString("fee_type"),
          (Integer) resultSet.getObject("building_id"),
          (Integer) resultSet.getObject("flat_id")
        ));
      }
    }
    return feesList;
  }

  // Method to update a fee
  public Fees updateFee(int feeId, Fees updatedFee) throws SQLException {
    String sql = "UPDATE fees SET fee_name = ?, fee_description = ?, fee_amount = ?, fee_due_date = ?, fee_type = ?, building_id = ?, flat_id = ? WHERE fee_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, updatedFee.getFeeName());
      statement.setString(2, updatedFee.getFeeDescription());
      statement.setDouble(3, updatedFee.getFeeAmount());
      statement.setDate(4, new java.sql.Date(updatedFee.getFeeDueDate().getTime()));
      statement.setString(5, updatedFee.getFeeType());
      statement.setObject(6, updatedFee.getBuildingId(), Types.INTEGER);
      statement.setObject(7, updatedFee.getFlatId(), Types.INTEGER);
      statement.setInt(8, feeId);
      statement.executeUpdate();
      return updatedFee;
    }
  }

  // Method to delete a fee
  public boolean deleteFee(int feeId) throws SQLException {
    String sql = "DELETE FROM fees WHERE fee_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, feeId);
      int rowsDeleted = statement.executeUpdate();
      return rowsDeleted > 0;
    }
  }
}