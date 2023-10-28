package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.core.Company;

public class CompanyDAO {
  Connection connection;

  public CompanyDAO(Connection connection) {
    this.connection = connection;
  }

  // Create a new company (add a new row to the company table)
  public void createCompany(String name, double earnings) throws SQLException {
    try {
      String sql = "INSERT INTO company (company_name, company_earnings) VALUES (?, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, name);
      statement.setDouble(2, earnings);
      statement.executeUpdate(); // Execute the query
    } catch (SQLException e) {
      throw new SQLException("Error in creating company", e);
    }
  }

  // Read a company (get a row from the company table)
  public Company getCompanyByName(String name) throws SQLException {
    try {
      String sql = "SELECT * FROM company WHERE company_name = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, name);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        String companyName = resultSet.getString("company_name");
        double earnings = resultSet.getDouble("company_earnings");

        return new Company(companyName, earnings);
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw new SQLException("Error in getting company", e);
    }
  }

  // Read all companies (get all rows from the company table)
  public List<Company> getAllCompanies() throws SQLException {
    List<Company> companies = new ArrayList<>(); // Create a list to hold the companies

    try {
      String sql = "SELECT * FROM company";
      PreparedStatement statement = connection.prepareStatement(sql);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        String name = resultSet.getString("company_name");
        double earnings = resultSet.getDouble("company_earnings");
        Company company = new Company(name, earnings);
        companies.add(company); // Add the company to the list
      }

    } catch (SQLException e) {
      throw new SQLException("Error in getting companies", e);
    }

    return companies;
  }

  // Update a company (update a row in the company table)
  public void updateCompany(Company company) throws SQLException {
    try {
      String sql =
          "UPDATE company SET company_name = ?, company_earnings = ? WHERE company_name = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, company.getName());
      statement.setDouble(2, company.getEarnings());
      statement.setString(3, company.getName());

      int affectedRows = statement.executeUpdate();

      if (affectedRows > 0) {
        new Company(company.getName(), company.getEarnings());
      }
    } catch (SQLException e) {
      throw new SQLException("Error in updating company", e);
    }
  }

  // Delete a company (delete a row from the company table)
  public boolean deleteCompany(String name) throws SQLException {
    try {
      String sql = "DELETE FROM company WHERE company_name = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, name);

      int affectedRows = statement.executeUpdate();

      return affectedRows > 0;

    } catch (SQLException e) {
      throw new SQLException("Error in deleting company", e);
    }
  }
}
