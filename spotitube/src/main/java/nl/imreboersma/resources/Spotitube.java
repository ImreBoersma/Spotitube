package nl.imreboersma.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
class Spotitube {
  @GET
  @Path("/login")
  public Response login() {
    return null;
  }
}
