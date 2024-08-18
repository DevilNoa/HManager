package org.example.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.example.core.Payments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentsDAOTest {

  private Connection mockConnection;
  private PreparedStatement mockStatement;
  private ResultSet mockResultSet;

  @BeforeEach
  void setUp() throws SQLException {
    mockConnection = mock(Connection.class);
    mockStatement = mock(PreparedStatement.class);
    mockResultSet = mock(ResultSet.class);
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
  }

  @Test
  void createPaymentSuccessfully() throws SQLException {
    PaymentsDAO paymentsDAO = new PaymentsDAO(mockConnection);
    Payments payment = new Payments(1, 1, 1, new java.util.Date(), 100.0, "Credit Card");
    paymentsDAO.createPayment(payment);

    verify(mockStatement).setInt(1, 1);
    verify(mockStatement).setInt(2, 1);
    verify(mockStatement).setDate(eq(3), any(java.sql.Date.class));
    verify(mockStatement).setDouble(4, 100.0);
    verify(mockStatement).setString(5, "Credit Card");
    verify(mockStatement).executeUpdate();
  }

  @Test
  void createPaymentThrowsSQLException() throws SQLException {
    doThrow(new SQLException("SQL error")).when(mockStatement).executeUpdate();

    PaymentsDAO paymentsDAO = new PaymentsDAO(mockConnection);
    Payments payment = new Payments(1, 1, 1, new java.util.Date(), 100.0, "Credit Card");

    SQLException exception =
        assertThrows(SQLException.class, () -> paymentsDAO.createPayment(payment));

    assertEquals("SQL error", exception.getMessage());
  }

  @Test
  void getPaymentByIdSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getInt("payment_id")).thenReturn(1);
    when(mockResultSet.getInt("resident_id")).thenReturn(1);
    when(mockResultSet.getInt("fee_id")).thenReturn(1);
    when(mockResultSet.getDate("payment_date")).thenReturn(new java.sql.Date(new java.util.Date().getTime()));
    when(mockResultSet.getDouble("payment_amount")).thenReturn(100.0);
    when(mockResultSet.getString("payment_method")).thenReturn("Credit Card");

    PaymentsDAO paymentsDAO = new PaymentsDAO(mockConnection);
    Payments payment = paymentsDAO.getPaymentById(1);

    assertNotNull(payment);
    assertEquals(1, payment.getPaymentId());
    assertEquals(1, payment.getResidentId());
    assertEquals(1, payment.getFeeId());
    assertEquals(100.0, payment.getPaymentAmount());
    assertEquals("Credit Card", payment.getPaymentMethod());
  }

  @Test
  void getPaymentByIdNotFound() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(false);

    PaymentsDAO paymentsDAO = new PaymentsDAO(mockConnection);
    Payments payment = paymentsDAO.getPaymentById(1);

    assertNull(payment);
  }

  @Test
  void getPaymentByIdThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    PaymentsDAO paymentsDAO = new PaymentsDAO(mockConnection);

    SQLException exception =
        assertThrows(SQLException.class, () -> paymentsDAO.getPaymentById(1));

    assertEquals("SQL error", exception.getMessage());
  }

  @Test
  void getAllPaymentsSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, true, false);
    when(mockResultSet.getInt("payment_id")).thenReturn(1, 2);
    when(mockResultSet.getInt("resident_id")).thenReturn(1, 2);
    when(mockResultSet.getInt("fee_id")).thenReturn(1, 2);
    when(mockResultSet.getDate("payment_date")).thenReturn(new java.sql.Date(new java.util.Date().getTime()));
    when(mockResultSet.getDouble("payment_amount")).thenReturn(100.0, 200.0);
    when(mockResultSet.getString("payment_method")).thenReturn("Credit Card", "Debit Card");

    PaymentsDAO paymentsDAO = new PaymentsDAO(mockConnection);
    List<Payments> payments = paymentsDAO.getAllPayments();

    assertNotNull(payments);
    assertEquals(2, payments.size());
    assertEquals(1, payments.get(0).getPaymentId());
    assertEquals(1, payments.get(0).getResidentId());
    assertEquals(1, payments.get(0).getFeeId());
    assertEquals(100.0, payments.get(0).getPaymentAmount());
    assertEquals("Credit Card", payments.get(0).getPaymentMethod());
    assertEquals(2, payments.get(1).getPaymentId());
    assertEquals(2, payments.get(1).getResidentId());
    assertEquals(2, payments.get(1).getFeeId());
    assertEquals(200.0, payments.get(1).getPaymentAmount());
    assertEquals("Debit Card", payments.get(1).getPaymentMethod());
  }

  @Test
  void getAllPaymentsThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    PaymentsDAO paymentsDAO = new PaymentsDAO(mockConnection);

    SQLException exception = assertThrows(SQLException.class, paymentsDAO::getAllPayments);

    assertEquals("SQL error", exception.getMessage());
  }

  @Test
  void updatePaymentSuccessfully() throws SQLException {
    when(mockStatement.executeUpdate()).thenReturn(1);

    PaymentsDAO paymentsDAO = new PaymentsDAO(mockConnection);
    Payments updatedPayment = new Payments(1, 1, 1, new java.util.Date(), 200.0, "Debit Card");
    Payments result = paymentsDAO.updatePayment(1, updatedPayment);

    assertNotNull(result);
    assertEquals(1, result.getPaymentId());
    assertEquals(1, result.getResidentId());
    assertEquals(1, result.getFeeId());
    assertEquals(200.0, result.getPaymentAmount());
    assertEquals("Debit Card", result.getPaymentMethod());
  }

  @Test
  void updatePaymentThrowsSQLException() throws SQLException {
    when(mockStatement.executeUpdate()).thenThrow(new SQLException("SQL error"));

    PaymentsDAO paymentsDAO = new PaymentsDAO(mockConnection);
    Payments updatedPayment = new Payments(1, 1, 1, new java.util.Date(), 200.0, "Debit Card");

    SQLException exception =
        assertThrows(SQLException.class, () -> paymentsDAO.updatePayment(1, updatedPayment));

    assertEquals("SQL error", exception.getMessage());
  }

  @Test
  void deletePaymentSuccessfully() throws SQLException {
    when(mockStatement.executeUpdate()).thenReturn(1);

    PaymentsDAO paymentsDAO = new PaymentsDAO(mockConnection);
    boolean result = paymentsDAO.deletePayment(1);

    assertTrue(result);
  }

  @Test
  void deletePaymentNotFound() throws SQLException {
    when(mockStatement.executeUpdate()).thenReturn(0);

    PaymentsDAO paymentsDAO = new PaymentsDAO(mockConnection);
    boolean result = paymentsDAO.deletePayment(1);

    assertFalse(result);
  }

  @Test
  void deletePaymentThrowsSQLException() throws SQLException {
    when(mockStatement.executeUpdate()).thenThrow(new SQLException("SQL error"));

    PaymentsDAO paymentsDAO = new PaymentsDAO(mockConnection);

    SQLException exception =
        assertThrows(SQLException.class, () -> paymentsDAO.deletePayment(1));

    assertEquals("SQL error", exception.getMessage());
  }
}