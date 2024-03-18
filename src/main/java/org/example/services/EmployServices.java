package org.example.services;

import java.sql.SQLException;
import java.util.List;
import org.example.core.Employ;
import org.example.dao.EmployDAO;

public class EmployServices {
  static EmployDAO employDAO;

  public EmployServices(EmployDAO employDAO) {
    EmployServices.employDAO = employDAO;
  }

  // Method to create an employ
  public void addEmploy(Employ newEmploy) throws SQLException {
    employDAO.createEmploy(newEmploy);
  }

  // Method to get an employ by name
  public Employ getEmployByID(int id) throws SQLException {
    return employDAO.getEmployByID(id);
  }

  // Method to get all employs
  public List<Employ> getAllEmploys() throws SQLException {
    return employDAO.getAllEmploys();
  }

  // Method to update an employ
  public Employ updateEmploy(int id, Employ newEmploy) throws SQLException {
    return employDAO.updateEmploy(id, newEmploy);
  }

  // Method to delete an employ
  public static boolean deleteEmploy(int id) throws SQLException {
    return employDAO.deleteEmploy(id);
  }
}
