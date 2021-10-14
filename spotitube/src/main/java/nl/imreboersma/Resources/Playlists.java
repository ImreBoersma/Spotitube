package nl.imreboersma.Resources;

import nl.imreboersma.DAO.iPlaylistDAO;
import nl.imreboersma.DTO.PlaylistDTO;
import nl.imreboersma.DTO.PlaylistsDTO;
import nl.imreboersma.DTO.TrackDTO;
import nl.imreboersma.Domain.Playlist;
import nl.imreboersma.Domain.User;
import nl.imreboersma.Resources.filters.VerifyToken;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/playlists")
@VerifyToken
public class Playlists {
  private iPlaylistDAO playlistDAO;

  @GET
  @Path("/")
  public Response all(@Context SecurityContext securityContext) {
    final User user = (User) securityContext.getUserPrincipal();
    final ArrayList<Playlist> playlists = playlistDAO.getAllPlaylistsCheckOwner(user.getId());
    return createResponse(playlists).build();
  }

  @DELETE
  @Path("/{playlistId}")
  public Response delete(@Context SecurityContext securityContext, @PathParam("playlistId") int id) {
    if(!playlistDAO.exists(id))
      return Response.status(Response.Status.NOT_FOUND).build();
    playlistDAO.deletePlaylist(id);
    final User user = (User) securityContext.getUserPrincipal();
    final ArrayList<Playlist> playlists = playlistDAO.getAllPlaylistsCheckOwner(user.getId());
    return createResponse(playlists).build();
  }

  @POST
  @Path("/")
  public Response post(@Context SecurityContext securityContext, PlaylistDTO request) {
    if(request == null)
      return Response.status(Response.Status.BAD_REQUEST).build();
    final User user = (User) securityContext.getUserPrincipal();
    playlistDAO.addPlaylist(user.getId(), request.name);
    final ArrayList<Playlist> playlists = playlistDAO.getAllPlaylistsCheckOwner(user.getId());
    return createResponse(playlists).status(Response.Status.CREATED).build();
  }

  @PUT
  @Path("/{playlistId}")
  public Response edit(@Context SecurityContext securityContext, @PathParam("playlistId") int id, PlaylistDTO request) {
    if(request == null)
      return Response.status(Response.Status.BAD_REQUEST).build();
    if(!playlistDAO.exists(id))
      return Response.status(Response.Status.NOT_FOUND).build();
    playlistDAO.editPlaylist(id, request.name);
    final User user = (User) securityContext.getUserPrincipal();
    final ArrayList<Playlist> playlists = playlistDAO.getAllPlaylistsCheckOwner(user.getId());
    return createResponse(playlists).build();
  }

  private Response.ResponseBuilder createResponse(ArrayList<Playlist> playlists) {
    final PlaylistsDTO response = new PlaylistsDTO();
    response.length = playlists.stream().mapToInt(Playlist::getLength).sum();
    response.playlists = playlists.stream().map(playlist -> {
      final PlaylistDTO playlistDTO = new PlaylistDTO();
      playlistDTO.name = playlist.getName();
      playlistDTO.owner = playlist.isOwner();
      playlistDTO.id = playlist.getId();
      playlistDTO.tracks = playlist.getTracks().stream().map(track -> {
        final TrackDTO trackDTO = new TrackDTO();
        trackDTO.id = track.getId();
        trackDTO.title = track.getTitle();
        trackDTO.performer = track.getPerformer();
        trackDTO.duration = track.getDuration();
        trackDTO.album = track.getAlbum();
        trackDTO.playCount = track.getPlayCount();
        trackDTO.publicationDate = track.getPublicationDate() == null ? "" : track.getPublicationDate().toString();
        trackDTO.description = track.getDescription();
        trackDTO.offlineAvailable = track.isOfflineAvailable();
        return trackDTO;
      }).collect(Collectors.toCollection(ArrayList::new));
      return playlistDTO;
    }).collect(Collectors.toCollection(ArrayList::new));
    return Response.ok().entity(response);
  }

  @Inject
  public void setPlaylistDAO(iPlaylistDAO playlistDAO) {
    this.playlistDAO = playlistDAO;
  }

}
