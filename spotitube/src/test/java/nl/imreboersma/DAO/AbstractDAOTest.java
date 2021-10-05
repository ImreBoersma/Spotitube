package nl.imreboersma.DAO;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractDAOTest<T extends iDAO> {
  T dao;
  private DataSource dataSource;
  private Properties properties;

  //  TODO: Move this method to a more global place
  private void getProperty() {
    properties = new Properties();
    try {
      properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));

    } catch(IOException e) {
      e.printStackTrace();
    }

  }

  @BeforeAll
  void setup() {
    if(dataSource == null) {
      BasicDataSource dataSource = new BasicDataSource();
      dataSource.setDriverClassName(properties.getProperty("driver"));
      dataSource.setUrl(properties.getProperty("uri"));
      dataSource.setUsername(properties.getProperty("user"));
      dataSource.setPassword(properties.getProperty("password"));
      this.dataSource = dataSource;
    }
    dao.setDataSource(this.dataSource);
  }

  @BeforeEach
  void startTransaction() {
    try(Connection connection = dataSource.getConnection()) {
      connection.createStatement().execute("BEGIN TRANSACTION");
    } catch(SQLException e) {
      fail(e);
    }
  }

  @AfterEach
  void rollbackTransaction() {
    try(Connection connection = dataSource.getConnection()) {
      connection.createStatement().execute("ROLLBACK TRANSACTION");
    } catch(SQLException e) {
      fail(e);
    }
  }
}
