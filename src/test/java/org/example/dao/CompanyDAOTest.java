package org.example.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.example.core.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyDAOTest {
  private Connection mockConnection;
  private PreparedStatement mockStatement;
  private ResultSet mockResultSet;
  private CompanyDAO companyDAO;

  @BeforeEach
  void setUp() throws SQLException {
    mockConnection = mock(Connection.class);
    mockStatement = mock(PreparedStatement.class);
    mockResultSet = mock(ResultSet.class);
    companyDAO = new CompanyDAO(mockConnection);

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
  }

  @Test
  void createCompanySuccessfully() throws SQLException {
    companyDAO.createCompany("Company1", 1000.0);

    verify(mockStatement).setString(1, "Company1");
    verify(mockStatement).setDouble(2, 1000.0);
    verify(mockStatement).executeUpdate();
  }

  @Test
  void createCompanyThrowsSQLException() throws SQLException {
    doThrow(new SQLException("SQL error")).when(mockStatement).executeUpdate();

    SQLException exception =
        assertThrows(SQLException.class, () -> companyDAO.createCompany("Company1", 1000.0));

    assertEquals("Error in creating company", exception.getMessage());
  }

  @Test
  void getCompanyByNameSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getString("company_name")).thenReturn("Company1");
    when(mockResultSet.getDouble("company_earnings")).thenReturn(1000.0);

    Company company = companyDAO.getCompanyByName("Company1");

    assertNotNull(company);
    assertEquals("Company1", company.getName());
    assertEquals(1000.0, company.getEarnings());
  }

  @Test
  void getCompanyByNameNotFound() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(false);

    Company company = companyDAO.getCompanyByName("NonExistentCompany");

    assertNull(company);
  }

  @Test
  void getCompanyByNameThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    SQLException exception =
        assertThrows(SQLException.class, () -> companyDAO.getCompanyByName("Company1"));

    assertEquals("Error in getting company", exception.getMessage());
  }

  @Test
  void getAllCompaniesSuccessfully() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, true, false);
    when(mockResultSet.getString("company_name")).thenReturn("Company1", "Company2");
    when(mockResultSet.getDouble("company_earnings")).thenReturn(1000.0, 2000.0);

    List<Company> companies = companyDAO.getAllCompanies();

    assertNotNull(companies);
    assertEquals(2, companies.size());
    assertEquals("Company1", companies.get(0).getName());
    assertEquals(1000.0, companies.get(0).getEarnings());
    assertEquals("Company2", companies.get(1).getName());
    assertEquals(2000.0, companies.get(1).getEarnings());
  }

  @Test
  void getAllCompaniesThrowsSQLException() throws SQLException {
    when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

    SQLException exception = assertThrows(SQLException.class, () -> companyDAO.getAllCompanies());

    assertEquals("Error in getting companies", exception.getMessage());
  }

  @Test
  void updateCompanySuccessfully() throws SQLException {
    PreparedStatement mockUpdateCompanyStatement = mock(PreparedStatement.class);
    PreparedStatement mockUpdateEmployStatement = mock(PreparedStatement.class);

    when(mockConnection.prepareStatement("UPDATE company SET company_name = ?, company_earnings = ? WHERE company_name = ?"))
            .thenReturn(mockUpdateCompanyStatement);
    when(mockConnection.prepareStatement("UPDATE employ SET company_name = ? WHERE company_name = ?"))
            .thenReturn(mockUpdateEmployStatement);
    when(mockUpdateCompanyStatement.executeUpdate()).thenReturn(1);
    when(mockUpdateEmployStatement.executeUpdate()).thenReturn(1);

    Company newCompany = new Company("Company1", 2000.0);
    companyDAO.updateCompany("Company1", newCompany);

    verify(mockUpdateCompanyStatement, times(1)).setString(1, "Company1");
    verify(mockUpdateCompanyStatement, times(1)).setDouble(2, 2000.0);
    verify(mockUpdateCompanyStatement, times(1)).setString(3, "Company1");
    verify(mockUpdateCompanyStatement, times(1)).executeUpdate();

    verify(mockUpdateEmployStatement, times(1)).setString(1, "Company1");
    verify(mockUpdateEmployStatement, times(1)).setString(2, "Company1");
    verify(mockUpdateEmployStatement, times(1)).executeUpdate();
  }

  @Test
  void updateCompanyNotFound() throws SQLException {
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(false);

    Company newCompany = new Company("Company1", 2000.0);

    doThrow(new SQLException("Error in updating company")).when(mockStatement).executeUpdate();

    SQLException exception =
            assertThrows(
                    SQLException.class, () -> companyDAO.updateCompany("NonExistentCompany", newCompany));

    assertEquals("Error in updating company", exception.getMessage());
  }

  @Test
  void updateCompanyThrowsSQLException() throws SQLException {
    PreparedStatement mockUpdateStatement = mock(PreparedStatement.class);
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockUpdateStatement);
    when(mockUpdateStatement.executeUpdate()).thenThrow(new SQLException("SQL error"));

    Company newCompany = new Company("Company1", 2000.0);

    SQLException exception =
        assertThrows(SQLException.class, () -> companyDAO.updateCompany("Company1", newCompany));

    assertEquals("Error in updating company", exception.getMessage());
  }

  @Test
  void deleteCompanySuccessfully() throws SQLException {
    when(mockStatement.executeUpdate()).thenReturn(1);

    boolean result = companyDAO.deleteCompany("Company1");

    assertTrue(result);
  }

  @Test
  void deleteCompanyNotFound() throws SQLException {
    when(mockStatement.executeUpdate()).thenReturn(0);

    boolean result = companyDAO.deleteCompany("Company1");

    assertFalse(result);
  }

  @Test
  void deleteCompanyThrowsSQLException() throws SQLException {
    when(mockStatement.executeUpdate()).thenThrow(new SQLException("SQL error"));

    SQLException exception =
        assertThrows(SQLException.class, () -> companyDAO.deleteCompany("Company1"));

    assertEquals("Error in deleting company", exception.getMessage());
  }
}
