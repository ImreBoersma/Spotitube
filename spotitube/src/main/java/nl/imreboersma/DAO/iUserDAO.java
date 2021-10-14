package nl.imreboersma.DAO;

import nl.imreboersma.Domain.User;

import java.util.Optional;

public interface iUserDAO extends iDAO {
  Optional<User> login(String username, String password);

  Optional<User> getUserFromToken(String token);

}
