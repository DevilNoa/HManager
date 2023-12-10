package org.example.services;

import java.sql.SQLException;
import java.util.List;
import org.example.core.Residents;
import org.example.dao.ResidentsDAO;

public class ResidentsServices {
  ResidentsDAO residentsDAO;

  public ResidentsServices(ResidentsDAO residentsDAO) {
    this.residentsDAO = residentsDAO;
  }

  // Get all residents
  public List<Residents> getAllResidents() throws SQLException {
    return residentsDAO.getAllResidents();
  }

  // Get residents by flat
  public List<Residents> getResidentsByFlat(int flat) throws SQLException {
    return residentsDAO.getResidentsByFlat(flat);
  }

  // Get residents by building
  public List<Residents> getResidentsByBuilding(String building) throws SQLException {
    return residentsDAO.getResidentsByBuilding(building);
  }

  // Get residents by building and flat
  public List<Residents> getResidentsByBuildingAndFlat(String building, int flat)
      throws SQLException {
    return residentsDAO.getResidentsByBuildingAndFlat(building, flat);
  }

  // Ger residents by name
  public List<Residents> getResidentsByName(String name) throws SQLException {
    return residentsDAO.getResidentsByName(name);
  }

  // Get residents by building and flat and name
  public List<Residents> getResidentsByBuildingAndFlatAndName(
      String building, int flat, String name) throws SQLException {
    return residentsDAO.getResidentsByBuildingAndFlatAndName(building, flat, name);
  }

  // Create resident
  public void addResident(Residents newResident) throws SQLException {
    residentsDAO.createResident(newResident);
  }

  // Update resident
  public void updateResident(Residents updatedResident) throws SQLException {
    residentsDAO.updateResident(updatedResident);
  }

  // Delete resident
  public void deleteResident(int id) throws SQLException {
    residentsDAO.deleteResident(id);
  }
}
