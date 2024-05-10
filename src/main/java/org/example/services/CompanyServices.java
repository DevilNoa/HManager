package org.example.services;

import java.sql.SQLException;
import java.util.List;
import org.example.core.Company;
import org.example.dao.CompanyDAO;

public class CompanyServices {
  CompanyDAO companyDAO;

  // Constructor to initialize CompanyServices with CompanyDAO instance
  public CompanyServices(CompanyDAO companyDAO) {
    this.companyDAO = companyDAO;
  }

  // Method to create a company
  public void addCompany(Company newCompany) throws SQLException {
    companyDAO.createCompany(newCompany.getName(), newCompany.getEarnings());
  }

  // Method to get a company by name
  public Company getCompanyByName(String name) throws SQLException {
    return companyDAO.getCompanyByName(name);
  }

  // Method to get all companies
  public List<Company> getAllCompanies() throws SQLException {
    return companyDAO.getAllCompanies();
  }

  // Method to update a company
  public Company updateCompany(String oldName, Company newCompany) throws SQLException {
    companyDAO.updateCompany(oldName, newCompany);
    return newCompany;
  }

  // Method to delete a company
  public boolean deleteCompany(String name) throws SQLException {
    return companyDAO.deleteCompany(name);
  }
}
