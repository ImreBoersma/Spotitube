package nl.imreboersma.resources.filters;

import nl.imreboersma.DAO.iUserDAO;
import nl.imreboersma.domain.User;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.util.Optional;

@Provider
@VerifyToken
public class VerifyTokenFilter implements ContainerRequestFilter {
  final public static String KEY = "token";

  private final iUserDAO userDAO;
  private User user;

  @Inject
  VerifyTokenFilter(iUserDAO userDAO) {
    this.userDAO = userDAO;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public void filter(ContainerRequestContext containerRequestContext) {
    String token = containerRequestContext.getUriInfo().getQueryParameters().getFirst(KEY);
    if(token == null)
      throw new BadRequestException();
    Optional<User> optionalUser = userDAO.getUserFromToken(token);
    optionalUser.ifPresent(this::setUser);

    if(optionalUser.isEmpty())
      throw new NotAuthorizedException("Not authorized");
  }
}
