package org.example.config;

import io.dropwizard.core.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HManagerConfiguration extends Configuration {
  // Declaring connection to the database
  private final Connection connection;

  // Constructor for initializing thr configuration
  public HManagerConfiguration() {
    try {
      // DB connection parameters
      String jdbcURL = "jdbc:postgresql://localhost:5433/postgres";
      String username = "postgres";
      String password = "admin";

      // Establishing db connection using JDBC
      connection = DriverManager.getConnection(jdbcURL, username, password);
      System.out.println("Connected to the database server.");
    } catch (SQLException e) {
      // Handling SQLException that may occur during connection to db
      System.out.println("Error connecting to the database server.");
      throw new RuntimeException(e);
    }
  }

  public Connection getConnection() {
    return connection;
  }
}
