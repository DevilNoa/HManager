package org.example.services;

import java.sql.SQLException;
import java.util.List;
import org.example.core.Employ;
import org.example.dao.EmployDAO;

public class EmployServices {
  EmployDAO employDAO;

  public EmployServices(EmployDAO employDAO) {
    this.employDAO = employDAO;
  }

  // Method to create an employ
  public void addEmploy(Employ newEmploy) throws SQLException {
    employDAO.createEmploy(newEmploy);
  }

  // Method to get an employ by name
  public Employ getEmployByName(String name) throws SQLException {
    return employDAO.getEmployByName(name);
  }

  // Method to get all employs
  public List<Employ> getAllEmploys() throws SQLException {
    return employDAO.getAllEmploys();
  }

  // Method to update an employ
  public Employ updateEmploy(String name, Employ newEmploy) throws SQLException {
    return employDAO.updateEmploy(name, newEmploy);
  }

  // Method to delete an employ
  public boolean deleteEmploy(String name) throws SQLException {
    return employDAO.deleteEmploy(name);
  }
}
