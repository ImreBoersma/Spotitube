package nl.imreboersma.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/track")
class Track {
  @GET
  public Response getAllTracks() {
    return null;
  }
}
