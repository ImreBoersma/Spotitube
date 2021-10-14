package nl.imreboersma.Resources.playlists;

import nl.imreboersma.DAO.iPlaylistDAO;
import nl.imreboersma.DAO.iTrackDAO;
import nl.imreboersma.DTO.TrackDTO;
import nl.imreboersma.DTO.playlists.TracksDTO;
import nl.imreboersma.Domain.Track;
import nl.imreboersma.Resources.filters.VerifyToken;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/playlists/{playlistId}/tracks")
@VerifyToken
public class Tracks {
  private iPlaylistDAO playlistDAO;
  private iTrackDAO trackDAO;

  @GET
  public Response all(@PathParam("playlistId") int playlistId) {
    if(!playlistDAO.exists(playlistId))
      return Response.status(Response.Status.NOT_FOUND).build();
    ArrayList<Track> tracks = playlistDAO.getAllTracks(playlistId);
    return createResponse(tracks).build();
  }

  @DELETE
  @Path("{trackId}")
  public Response delete(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId) {
    if(!playlistDAO.exists(playlistId))
      return Response.status(Response.Status.NOT_FOUND).build();
    if(!trackDAO.trackExistsInPlaylist(playlistId, trackId))
      return Response.status(Response.Status.NOT_FOUND).build();
    trackDAO.deleteTrackInPlaylist(playlistId, trackId);
    ArrayList<Track> tracks = playlistDAO.getAllTracks(playlistId);
    return createResponse(tracks).build();
  }

  @POST
  public Response post(@PathParam("playlistId") int playlistId, TrackDTO request) {
    if(!playlistDAO.exists(playlistId))
      return Response.status(Response.Status.NOT_FOUND).build();
    if(request == null)
      return Response.status(Response.Status.BAD_REQUEST).build();
    trackDAO.addToPlaylist(playlistId, request.id, request.offlineAvailable);
    ArrayList<Track> tracks = playlistDAO.getAllTracks(playlistId);
    return createResponse(tracks).status(Response.Status.CREATED).build();
  }

  public Response.ResponseBuilder createResponse(ArrayList<Track> tracks) {
    final TracksDTO response = new TracksDTO();
    response.tracks = tracks.stream().map(track -> {
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
    return Response.ok().entity(response);
  }

  @Inject
  public void setPlaylistDAO(iPlaylistDAO playlistDAO) {
    this.playlistDAO = playlistDAO;
  }

  @Inject
  public void setTrackDAO(iTrackDAO trackDAO) {
    this.trackDAO = trackDAO;
  }

}
