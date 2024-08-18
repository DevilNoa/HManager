package org.example.dao;

import org.example.core.Payments;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentsDAO {
  private final Connection connection;

  public PaymentsDAO(Connection connection) {
    this.connection = connection;
  }

  // Method to create a payment
  public void createPayment(Payments payment) throws SQLException {
    String sql =
        "INSERT INTO payments (resident_id, fee_id, payment_date, payment_amount, payment_method) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, payment.getResidentId());
      statement.setInt(2, payment.getFeeId());
      statement.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
      statement.setDouble(4, payment.getPaymentAmount());
      statement.setString(5, payment.getPaymentMethod());
      statement.executeUpdate();
    }
  }

  // Method to get a payment by ID
  public Payments getPaymentById(int paymentId) throws SQLException {
    String sql = "SELECT * FROM payments WHERE payment_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, paymentId);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return new Payments(
            resultSet.getInt("payment_id"),
            resultSet.getInt("resident_id"),
            resultSet.getInt("fee_id"),
            resultSet.getDate("payment_date"),
            resultSet.getDouble("payment_amount"),
            resultSet.getString("payment_method"));
      } else {
        return null;
      }
    }
  }

  // Method to get all payments
  public List<Payments> getAllPayments() throws SQLException {
    List<Payments> paymentsList = new ArrayList<>();
    String sql = "SELECT * FROM payments";
    try (PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        paymentsList.add(
            new Payments(
                resultSet.getInt("payment_id"),
                resultSet.getInt("resident_id"),
                resultSet.getInt("fee_id"),
                resultSet.getDate("payment_date"),
                resultSet.getDouble("payment_amount"),
                resultSet.getString("payment_method")));
      }
    }
    return paymentsList;
  }

  // Method to update a payment
  public Payments updatePayment(int paymentId, Payments updatedPayment) throws SQLException {
    String sql =
        "UPDATE payments SET resident_id = ?, fee_id = ?, payment_date = ?, payment_amount = ?, payment_method = ? WHERE payment_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, updatedPayment.getResidentId());
      statement.setInt(2, updatedPayment.getFeeId());
      statement.setDate(3, new java.sql.Date(updatedPayment.getPaymentDate().getTime()));
      statement.setDouble(4, updatedPayment.getPaymentAmount());
      statement.setString(5, updatedPayment.getPaymentMethod());
      statement.setInt(6, paymentId);
      statement.executeUpdate();
      return updatedPayment;
    }
  }

  // Method to delete a payment
  public boolean deletePayment(int paymentId) throws SQLException {
    String sql = "DELETE FROM payments WHERE payment_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, paymentId);
      int rowsDeleted = statement.executeUpdate();
      return rowsDeleted > 0;
    }
  }
}
