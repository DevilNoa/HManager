package org.example.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class Fees {

  @JsonProperty private int feeId;
  @JsonProperty private String feeName;
  @JsonProperty private String feeDescription;
  @JsonProperty private double feeAmount;
  @JsonProperty private Date feeDueDate;
  @JsonProperty private String feeType;
  @JsonProperty private Integer buildingId;
  @JsonProperty private Integer flatId;

  public Fees() {}

  public Fees(
      int feeId,
      String feeName,
      String feeDescription,
      double feeAmount,
      Date feeDueDate,
      String feeType,
      Integer buildingId,
      Integer flatId) {
    this.feeId = feeId;
    this.feeName = feeName;
    this.feeDescription = feeDescription;
    this.feeAmount = feeAmount;
    this.feeDueDate = feeDueDate;
    this.feeType = feeType;
    this.buildingId = buildingId;
    this.flatId = flatId;
  }

  public int getFeeId() {
    return feeId;
  }

  public void setFeeId(int feeId) {
    this.feeId = feeId;
  }

  public String getFeeName() {
    return feeName;
  }

  public void setFeeName(String feeName) {
    this.feeName = feeName;
  }

  public String getFeeDescription() {
    return feeDescription;
  }

  public void setFeeDescription(String feeDescription) {
    this.feeDescription = feeDescription;
  }

  public double getFeeAmount() {
    return feeAmount;
  }

  public void setFeeAmount(double feeAmount) {
    this.feeAmount = feeAmount;
  }

  public Date getFeeDueDate() {
    return feeDueDate;
  }

  public void setFeeDueDate(Date feeDueDate) {
    this.feeDueDate = feeDueDate;
  }

  public String getFeeType() {
    return feeType;
  }

  public void setFeeType(String feeType) {
    this.feeType = feeType;
  }

  public Integer getBuildingId() {
    return buildingId;
  }

  public void setBuildingId(Integer buildingId) {
    this.buildingId = buildingId;
  }

  public Integer getFlatId() {
    return flatId;
  }

  public void setFlatId(Integer flatId) {
    this.flatId = flatId;
  }
}
