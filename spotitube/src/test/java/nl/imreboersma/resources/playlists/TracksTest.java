package nl.imreboersma.resources.playlists;

import nl.imreboersma.DAO.iPlaylistDAO;
import nl.imreboersma.DAO.iTrackDAO;
import nl.imreboersma.DTO.TrackDTO;
import nl.imreboersma.DTO.playlists.TracksDTO;
import nl.imreboersma.domain.Track;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TracksTest {
  private static Tracks playlistsTracksAPI;
  private iTrackDAO trackDAO;
  private iPlaylistDAO playlistDAO;
  private ArrayList<Track> tracksData;

  @BeforeAll
  public static void setPlaylistsTracksAPI() {
    playlistsTracksAPI = new Tracks();
  }

  @BeforeEach
  public void mockDAOs() {
    playlistDAO = mock(iPlaylistDAO.class);
    trackDAO = mock(iTrackDAO.class);

    playlistsTracksAPI.setPlaylistDAO(playlistDAO);
    playlistsTracksAPI.setTrackDAO(trackDAO);
  }

  @BeforeEach
  public void generateTracks() {
    tracksData = new ArrayList<>();
    tracksData.add(new Track(3, "Ocean and a rock", "Lisa Hannigan", 337, "Sea sew", 0, null, "", false));
    tracksData.add(new Track(4, "So Long, Marianne", "Leonard Cohen", 546, "Songs of Leonard Cohen", 4, null, "", true));
  }

  @Test
  void allPlaylistTracksPlaylistNotFoundTest() {
    when(playlistDAO.exists(1)).thenReturn(false);
    final Response response = playlistsTracksAPI.all(1);
    assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
  }

  @Test
  void allPlaylistTracksSuccessTest() {
    when(playlistDAO.exists(1)).thenReturn(true);
    when(playlistDAO.getAllTracks(1)).thenReturn(tracksData);
    final Response response = playlistsTracksAPI.all(1);
    assertEquals(Response.Status.OK, response.getStatusInfo());

    final TracksDTO tracksDTO = (TracksDTO) response.getEntity();
    assertNotNull(tracksDTO);

    assertEquals(2, tracksDTO.tracks.size());
    TrackDTO trackDTO = tracksDTO.tracks.get(0);
    assertEquals(3, trackDTO.id);
    assertEquals("Ocean and a rock", trackDTO.title);
    assertEquals("Lisa Hannigan", trackDTO.performer);
    assertEquals(337, trackDTO.duration);
    assertEquals("Sea sew", trackDTO.album);
    assertEquals(0, trackDTO.playCount);
    assertEquals("", trackDTO.publicationDate);
    assertEquals("", trackDTO.description);
    assertFalse(trackDTO.offlineAvailable);
  }

  @Test
  void deletePlaylistsTrackPlaylistNotFoundTest() {
    when(playlistDAO.exists(1)).thenReturn(false);
    final Response response = playlistsTracksAPI.delete(1, 92123);
    assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
  }

  @Test
  void deletePlaylistsTrackNotFoundTest() {
    when(trackDAO.trackExistsInPlaylist(1, 1)).thenReturn(false);
    final Response response = playlistsTracksAPI.delete(1, 1);
    assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
  }

  @Test
  void deletePlaylistsTrackSuccessTest() {
    when(playlistDAO.exists(1)).thenReturn(true);
    when(trackDAO.trackExistsInPlaylist(1, 1)).thenReturn(true);
    tracksData.remove(0);
    when(playlistDAO.getAllTracks(1)).thenReturn(tracksData);

    final Response response = playlistsTracksAPI.delete(1, 1);
    verify(trackDAO).deleteTrackInPlaylist(1, 1);
    assertEquals(Response.Status.OK, response.getStatusInfo());

    final TracksDTO tracksDTO = (TracksDTO) response.getEntity();
    assertNotNull(tracksDTO);

    assertEquals(1, tracksDTO.tracks.size());
  }

  @Test
  void postPlaylistsTrackPlaylistNotFoundTest() {
    when(playlistDAO.exists(1)).thenReturn(false);
    final Response response = playlistsTracksAPI.post(1, null);
    assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
  }

  @Test
  void postPlaylistsTrackBadRequestTest() {
    when(playlistDAO.exists(1)).thenReturn(true);
    final Response response = playlistsTracksAPI.post(1, null);
    assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
  }

  @Test
  void postPlaylistsTrackSuccessTest() {
    when(playlistDAO.exists(1)).thenReturn(true);
    TrackDTO request = new TrackDTO();
    request.id = 3;
    request.offlineAvailable = true;

    final Response response = playlistsTracksAPI.post(1, request);
    verify(trackDAO).addToPlaylist(1, 3, true);
    assertEquals(Response.Status.CREATED, response.getStatusInfo());
  }

}