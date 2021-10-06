package nl.imreboersma.resources;

import nl.imreboersma.DAO.iPlaylistDAO;
import nl.imreboersma.domain.User;
import nl.imreboersma.resources.filters.VerifyToken;
import nl.imreboersma.resources.filters.VerifyTokenFilter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/playlist")
@VerifyToken
public class Playlist {
  private iPlaylistDAO playlistDAO;

  @GET
  public Response all(@Context VerifyTokenFilter verifyTokenFilter) {
    final User user = verifyTokenFilter.getUser();
    final ArrayList<nl.imreboersma.domain.Playlist> playlists = playlistDAO.getAllPlaylistsCheckOwner(user.getId());
    return Response.ok().build();
  }
}
