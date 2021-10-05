package nl.imreboersma.resources;

import nl.imreboersma.DAO.iUserDAO;
import nl.imreboersma.DTO.UserDTO;
import nl.imreboersma.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationTest {
  private static Authentication authentication;
  private iUserDAO userDAO;

  @BeforeAll
  public static void beforeAll() {
    authentication = new Authentication();
  }

  @BeforeEach
  void setUp() {
    userDAO = mock(iUserDAO.class);
    authentication.setUserDAO(userDAO);
  }

  @Test
  void loginBadRequest() {
    // Act
    Response response = authentication.login(null);

    // Assert
    assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    assertNull(response.getEntity());
  }

  @Test
  void loginUnauthorized() {
    // Arrange
    User user = new User();
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setToken("SecretToken");

    when(userDAO.login("admin", "admin")).thenReturn(Optional.of(user));

    UserDTO userDTO = new UserDTO();
    userDTO.user = "testUser";
    userDTO.password = "password";

    // Act
    Response response = authentication.login(userDTO);

    // Assert
    assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
    assertNull(response.getEntity());
  }

  @Test
  void loginOk() {
    User user = new User();
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setToken("secretToken");
    when(userDAO.login("admin", "admin")).thenReturn(Optional.of(user));

    UserDTO userDTO = new UserDTO();
    userDTO.user = "admin";
    userDTO.password = "admin";
    Response response = authentication.login(userDTO);

    assertEquals(Response.Status.OK, response.getStatusInfo());

    UserDTO responseDTO = (UserDTO) response.getEntity();
    assertEquals("John Doe", responseDTO.user);
    assertEquals("secretToken", responseDTO.token);

  }
}