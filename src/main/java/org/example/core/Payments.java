package org.example.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class Payments {

  @JsonProperty private int paymentId;
  @JsonProperty private int residentId;
  @JsonProperty private int feeId;
  @JsonProperty private Date paymentDate;
  @JsonProperty private double paymentAmount;
  @JsonProperty private String paymentMethod;

  public Payments() {}

  public Payments(
      int paymentId,
      int residentId,
      int feeId,
      Date paymentDate,
      double paymentAmount,
      String paymentMethod) {
    this.paymentId = paymentId;
    this.residentId = residentId;
    this.feeId = feeId;
    this.paymentDate = paymentDate;
    this.paymentAmount = paymentAmount;
    this.paymentMethod = paymentMethod;
  }

  public int getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(int paymentId) {
    this.paymentId = paymentId;
  }

  public int getResidentId() {
    return residentId;
  }

  public void setResidentId(int residentId) {
    this.residentId = residentId;
  }

  public int getFeeId() {
    return feeId;
  }

  public void setFeeId(int feeId) {
    this.feeId = feeId;
  }

  public Date getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  public double getPaymentAmount() {
    return paymentAmount;
  }

  public void setPaymentAmount(double paymentAmount) {
    this.paymentAmount = paymentAmount;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }
}
