package org.example.services;

import org.example.core.Payments;
import org.example.dao.PaymentsDAO;

import java.sql.SQLException;
import java.util.List;

public class PaymentsServices {
  private final PaymentsDAO paymentsDAO;

  public PaymentsServices(PaymentsDAO paymentsDAO) {
    this.paymentsDAO = paymentsDAO;
  }

  // Method to create a payment
  public void addPayment(Payments payment) throws SQLException {
    paymentsDAO.createPayment(payment);
  }

  // Method to get a payment by ID
  public Payments getPaymentById(int paymentId) throws SQLException {
    return paymentsDAO.getPaymentById(paymentId);
  }

  // Method to get all payments
  public List<Payments> getAllPayments() throws SQLException {
    return paymentsDAO.getAllPayments();
  }

  // Method to update a payment
  public Payments updatePayment(int paymentId, Payments updatedPayment) throws SQLException {
    return paymentsDAO.updatePayment(paymentId, updatedPayment);
  }

  // Method to delete a payment
  public boolean deletePayment(int paymentId) throws SQLException {
    return paymentsDAO.deletePayment(paymentId);
  }
}
