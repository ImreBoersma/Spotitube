package nl.imreboersma.DAO;

import nl.imreboersma.domain.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class UserDAO implements iUserDAO {
  @Resource(name = "jdbc/Spotitube")
  DataSource dataSource;

  @Override
  public Optional<User> login(String username, String password) {
    try(Connection connection = dataSource.getConnection()) {
      // TODO: Fix the prepared statement
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM [user] WHERE [user].[username] = '" + username + "' AND [user].[password] = CONVERT(VARCHAR(128), HashBytes('SHA2_512', '" + password + "'), 1)");
      ResultSet resultSet = statement.executeQuery();

      return createUser(resultSet);
    } catch(SQLException e) {
      throw new InternalServerErrorException(e);
    }
  }

  @Override
  public Optional<User> getUserFromToken(String token) {
    try(Connection connection = dataSource.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM [user] WHERE [user].[token] = '53F8B70B-F0C2-4092-829A-65516AF24CCA'");
      ResultSet resultSet = statement.executeQuery();

      return createUser(resultSet);
    } catch(SQLException e) {
      throw new InternalServerErrorException(e);
    }
  }

  private Optional<User> createUser(ResultSet resultSet) throws SQLException {
    if(!resultSet.next())
      return Optional.empty();

    User user = new User();
    user.setId(resultSet.getInt("id"));
    user.setFirstName(resultSet.getString("first_name"));
    user.setLastName(resultSet.getString("last_name"));
    user.setUsername(resultSet.getString("username"));
    user.setToken(resultSet.getString("token"));
    return Optional.of(user);
  }

  @Override
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
}
