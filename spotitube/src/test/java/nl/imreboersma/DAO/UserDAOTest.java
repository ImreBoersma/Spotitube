package nl.imreboersma.DAO;

import nl.imreboersma.Domain.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest extends AbstractDAOTest<UserDAO> {
  UserDAOTest() {
    dao = new UserDAO();
  }

  @Test
  void loginUserEmptyTest() {
    assertEquals(Optional.empty(), dao.login("FeelsGoodMan", "password123"));
    assertEquals(Optional.empty(), dao.login("gibberish", "nonsense"));
  }

  @Test
  void loginUserExistsTest() {
    Optional<User> maybeUser = dao.login("imreboersma", "password");
    assertDoesNotThrow(maybeUser::get);
    if(maybeUser.isPresent()) {
      User user = maybeUser.get();
      assertEquals("imre", user.getFirstName());
      assertEquals("boersma", user.getLastName());
    }
  }

  @Test
  void getUserFromTokenEmptyTest() {
    assertEquals(Optional.empty(), dao.getUserFromToken("foobar"));
    assertEquals(Optional.empty(), dao.getUserFromToken("nonsensical"));
  }

  @Test
  void getUserFromToken() {
    Optional<User> maybeUser = dao.getUserFromToken("53F8B70B-F0C2-4092-829A-65516AF24CCA");
    assertDoesNotThrow(maybeUser::get);
    if(maybeUser.isPresent()) {
      User user = maybeUser.get();
      assertEquals("imre", user.getFirstName());
      assertEquals("boersma", user.getLastName());
    } else {
      fail("User not found!");
    }
  }
}
