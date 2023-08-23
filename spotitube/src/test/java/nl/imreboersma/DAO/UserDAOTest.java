package nl.imreboersma.DAO;

import nl.imreboersma.Domain.User;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        assertNull(user.getFirstName());
      assertNull(user.getLastName());
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
    assertThrows(NoSuchElementException.class, maybeUser::get);
      User user = maybeUser.orElse(null);
    if (user != null) {
      assertNull(user.getFirstName());
      assertNull(user.getLastName());
    }
  }

  @Override
  protected UserDAO createDAOMock() {
    UserDAO userDAO = mock(UserDAO.class);
    when(userDAO.login(any(), any())).thenReturn(Optional.empty());
    when(userDAO.getUserFromToken(any())).thenReturn(Optional.empty());
    when(userDAO.login("imreboersma", "password")).thenReturn(Optional.of(new User()));

    return userDAO;
  }
}
