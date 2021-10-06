package nl.imreboersma.DAO;

import nl.imreboersma.domain.User;

import java.util.Optional;

public interface iUserDAO {
  Optional<User> login(String username, String password);

  Optional<User> getUserFromToken(String token);

}
