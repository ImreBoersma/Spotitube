package nl.imreboersma.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandler {
  public static Connection createConnection() throws SQLException {
    DatabaseProperties databaseProperties = new DatabaseProperties();

    try {
      Class.forName(databaseProperties.getDriver());
    } catch(ClassNotFoundException e) {
      e.printStackTrace();
    }

    try(Connection connection = DriverManager.getConnection(databaseProperties.getConnectionString())) {
      return connection;
    } catch(SQLException e) {
      throw new SQLException(e);
    }
  }
}
