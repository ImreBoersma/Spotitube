package nl.imreboersma.resources;

import nl.imreboersma.DAO.iUserDAO;
import nl.imreboersma.DTO.UserDTO;
import nl.imreboersma.domain.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/login")
public class Authentication {

  private iUserDAO userDAO;

  @GET
  public Response getUser() {
    return Response.status(200).entity("getUser is called").build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(UserDTO request) {
    if(request == null)
      return Response.status(Response.Status.BAD_REQUEST).build();

    Optional<User> optionalUser = this.userDAO.login(request.user, request.password);
    // TODO: Fix that \/ throws NPE in tests
    if(optionalUser == null)
      return Response.status(Response.Status.UNAUTHORIZED).build();

    User user = optionalUser.get();
    UserDTO response = new UserDTO();
    response.user = user.getFullName();
    response.token = user.getToken();

    return Response.ok().entity(response).build();
  }

  @Inject
  public void setUserDAO(iUserDAO userDAO) {
    this.userDAO = userDAO;
  }
}
