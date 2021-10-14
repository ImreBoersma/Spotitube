package nl.imreboersma.resources.filters;

import nl.imreboersma.DAO.iUserDAO;
import nl.imreboersma.domain.User;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.security.Principal;
import java.util.Optional;

@Provider
@VerifyToken
public class VerifyTokenFilter implements ContainerRequestFilter {
  final public static String KEY = "token";

  private final iUserDAO userDAO;

  @Inject
  VerifyTokenFilter(iUserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public void filter(ContainerRequestContext containerRequestContext) {
    String token = containerRequestContext.getUriInfo().getQueryParameters().getFirst(KEY);
    if(token == null)
      throw new BadRequestException();
    Optional<User> optionalUser = userDAO.getUserFromToken(token);

    if(optionalUser.isEmpty())
      throw new NotAuthorizedException("Not authorized");

    containerRequestContext.setSecurityContext(new SecurityContext() {
      @Override
      public Principal getUserPrincipal() {
        return optionalUser.get();
      }

      @Override
      public boolean isUserInRole(String s) {
        return false;
      }

      @Override
      public boolean isSecure() {
        return containerRequestContext.getUriInfo().getAbsolutePath().toString().startsWith("https");
      }

      @Override
      public String getAuthenticationScheme() {
        return null;
      }
    });
  }
}
