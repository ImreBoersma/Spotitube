package nl.imreboersma.DAO;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;

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
    MockitoAnnotations.initMocks(this);

    dao = createDAOMock();
    dao.setDataSource(dataSource);
    Properties properties = new Properties();
  }

  protected abstract T createDAOMock();
}
