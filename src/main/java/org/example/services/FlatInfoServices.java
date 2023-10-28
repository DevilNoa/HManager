package org.example.services;

import org.example.core.FlatInfo;
import org.example.dao.FlatInfoDAO;

import java.sql.SQLException;
import java.util.List;

public class FlatInfoServices {
  FlatInfoDAO flatInfoDAO;

  public FlatInfoServices(FlatInfoDAO flatInfoDAO) {
    this.flatInfoDAO = flatInfoDAO;
  }

  // Method to create a flat info
  public void addFlatInfo(FlatInfo newFlatInfo) throws SQLException {
    flatInfoDAO.createFlatInfo(newFlatInfo);
  }

  // Method to get a flat info by building name and flat number
  public FlatInfo getFlatInfo(String buildingName, int flatNumber) throws SQLException {
    return flatInfoDAO.getFlatInfo(buildingName, flatNumber);
  }

  // Method to get all flats info for a building
  public List<FlatInfo> getAllFlatInfoForBuilding(String buildingName) throws SQLException {
    return flatInfoDAO.getAllFlatInfoForBuilding(buildingName);
  }

  // Method to update a flat info
  public FlatInfo updateFlatInfo(String buildingName, int flatNumber, FlatInfo newFlatInfo)
      throws SQLException {
    return flatInfoDAO.updateFlatInfo(buildingName, flatNumber, newFlatInfo);
  }

  // Method to delete a flat info
  public boolean deleteFlatInfo(String buildingName, int flatNumber) throws SQLException {
    return flatInfoDAO.deleteFlatInfo(buildingName, flatNumber);
  }
}
