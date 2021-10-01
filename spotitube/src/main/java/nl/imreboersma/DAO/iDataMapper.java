package nl.imreboersma.DAO;

import javax.sql.DataSource;
import java.util.List;

public interface iDataMapper {
  void setDatasource(DataSource dataSource);

  List<?> getAll(int userId);
}
