package org.example.services;

import org.example.core.Fees;
import org.example.dao.FeesDAO;

import java.sql.SQLException;
import java.util.List;

public class FeesServices {
  private final FeesDAO feesDAO;

  public FeesServices(FeesDAO feesDAO) {
    this.feesDAO = feesDAO;
  }

  // Method to add a fee
  public void addFee(Fees newFee) throws SQLException {
    feesDAO.createFee(newFee);
  }

  // Method to get a fee by ID
  public Fees getFeeById(int feeId) throws SQLException {
    return feesDAO.getFeeById(feeId);
  }

  // Method to get all fees
  public List<Fees> getAllFees() throws SQLException {
    return feesDAO.getAllFees();
  }

  // Method to update a fee
  public Fees updateFee(int feeId, Fees updatedFee) throws SQLException {
    return feesDAO.updateFee(feeId, updatedFee);
  }

  // Method to delete a fee
  public boolean deleteFee(int feeId) throws SQLException {
    return feesDAO.deleteFee(feeId);
  }
}
