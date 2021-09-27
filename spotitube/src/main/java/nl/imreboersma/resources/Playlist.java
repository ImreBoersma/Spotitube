package nl.imreboersma.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/playlist")
class Playlist {
  @GET
  @Path("/")
  public Response getAllPlaylists() {
    return null;
  }
}
