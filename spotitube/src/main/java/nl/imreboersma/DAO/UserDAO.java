package nl.imreboersma.DAO;

import nl.imreboersma.domain.User;

import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDAO implements iUserDAO {
  private DataSource dataSource;

  @Override
  public Optional<User> login(String username, String password) {
    try(Connection connection = dataSource.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM [user] WHERE [user].[username] = ? AND CONVERT(VARCHAR(128), HashBytes('SHA2_512', ?), 1)");
      statement.setString(1, username);
      statement.setString(2, password);
      ResultSet resultSet = statement.executeQuery();

      return createUser(resultSet);
    } catch(SQLException throwable) {
      throwable.printStackTrace();
      throw new InternalServerErrorException();
    }
  }

  @Override
  public Optional<User> getUserFromToken(String token) {
    try(Connection connection = dataSource.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM [user] WHERE [user].token = ?");
      statement.setString(1, token);
      ResultSet resultSet = statement.executeQuery();

      return createUser(resultSet);

    } catch(SQLException throwable) {
      throwable.printStackTrace();
      throw new InternalServerErrorException();
    }

  }

  @Override
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  private Optional<User> createUser(ResultSet resultSet) throws SQLException {
    if(!resultSet.next())
      return Optional.empty();

    User user = new User();
    user.setId(resultSet.getInt("id"));
    user.setFirstName(resultSet.getString("first_name"));
    user.setLastName(resultSet.getString("last_name"));
    user.setUsername(resultSet.getString("username"));
    user.setPassword(resultSet.getString("password"));
    user.setToken(resultSet.getString("token"));
    return Optional.of(user);

  }
}
