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

  /// Update a company (update a row in the company table)
  public void updateCompany(String oldName, Company company) throws SQLException {
    try {
      // Start a transaction
      connection.setAutoCommit(false);

      // First, update the 'company' table
      String sqlUpdateCompany = "UPDATE company SET company_name = ?, company_earnings = ? WHERE company_name = ?";
      PreparedStatement statementCompany = connection.prepareStatement(sqlUpdateCompany);
      statementCompany.setString(1, company.getName());
      statementCompany.setDouble(2, company.getEarnings());
      statementCompany.setString(3, oldName);
      statementCompany.executeUpdate();

      // Then, update the 'employ' table
      String sqlUpdateEmploy = "UPDATE employ SET company_name = ? WHERE company_name = ?";
      PreparedStatement statementEmploy = connection.prepareStatement(sqlUpdateEmploy);
      statementEmploy.setString(1, company.getName());
      statementEmploy.setString(2, oldName);
      statementEmploy.executeUpdate();

      // If both updates are successful, commit the transaction
      connection.commit();

      // Set auto commit back to true
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      // If there is an error, rollback the transaction
      connection.rollback();

      // Set auto commit back to true
      connection.setAutoCommit(true);

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
