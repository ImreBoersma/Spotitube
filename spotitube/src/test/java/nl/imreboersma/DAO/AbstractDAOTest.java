package nl.imreboersma.DAO;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractDAOTest<T extends iDAO> {
  private static DataSource dataSource;
  protected T dao;

  @BeforeAll
  void setup() {
    Properties properties = new Properties();
    try {
      properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
    } catch(IOException e) {
      fail(e);
    }

    if(dataSource == null) {
      SQLServerDataSource dataSource = new SQLServerDataSource();
      dataSource.setDatabaseName(properties.getProperty("databaseName"));
      dataSource.setIntegratedSecurity(true);
      dataSource.setServerName(properties.getProperty("serverName"));
      dataSource.setPortNumber(Integer.parseInt(properties.getProperty("portNumber")));

      AbstractDAOTest.dataSource = dataSource;
    }
    dao.setDataSource(AbstractDAOTest.dataSource);
  }
}
