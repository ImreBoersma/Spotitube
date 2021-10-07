package nl.imreboersma.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

class DatabasePropertiesTest {
  private Connection connection;
  private DatabaseProperties databaseProperties;

  @BeforeEach
  void setUp() {
    databaseProperties = new DatabaseProperties();
  }

  @Test
  void setUpConnection() {
    try {
      connection = DriverManager.getConnection(databaseProperties.getConnectionString());
    } catch(SQLException e) {
      fail(e);
    }
  }

  @AfterEach
  void tearDown() {
    try {
      connection.close();
    } catch(SQLException e) {
      fail(e);
    }
  }
}