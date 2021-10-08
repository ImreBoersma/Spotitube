package nl.imreboersma.resources;

import nl.imreboersma.DAO.iPlaylistDAO;
import nl.imreboersma.DAO.iTrackDAO;
import nl.imreboersma.DTO.TrackDTO;
import nl.imreboersma.DTO.playlists.TracksDTO;
import nl.imreboersma.domain.Track;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TracksTest {
  private static Tracks tracksAPI;
  private iTrackDAO trackDAO;
  private iPlaylistDAO playlistDAO;

  @BeforeAll
  public static void setTracksApi() {
    tracksAPI = new Tracks();
  }

  @BeforeEach
  public void mockDAOs() {
    playlistDAO = mock(iPlaylistDAO.class);
    trackDAO = mock(iTrackDAO.class);

    tracksAPI.setPlaylistDAO(playlistDAO);
    tracksAPI.setTrackDAO(trackDAO);
  }

  @Test
  void allNoPlaylistTest() {
    when(playlistDAO.exists(1)).thenReturn(false);
    Response response = tracksAPI.all(1);
    assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
  }

  @Test
  void allPlaylistExists() {
    when(playlistDAO.exists(1)).thenReturn(true);

    ArrayList<Track> tracks = new ArrayList<>();
    Track track = new Track();
    track.setId(1);
    track.setTitle("Foo Bar");
    track.setPerformer("Jane Doe");
    track.setDuration(418);
    track.setAlbum("Baz Lofi");
    track.setPlayCount(69);
    Date date = Date.from(Instant.now());
    track.setPublicationDate(date);
    track.setDescription("Hello world");
    track.setOfflineAvailable(true);
    tracks.add(track);
    when(trackDAO.getTracksNotInPlaylist(1)).thenReturn(tracks);

    Response response = tracksAPI.all(1);
    assertEquals(Response.Status.OK, response.getStatusInfo());

    TracksDTO tracksDTO = (TracksDTO) response.getEntity();
    assertNotNull(tracksDTO);
    assertEquals(1, tracksDTO.tracks.size());

    TrackDTO trackDTO = tracksDTO.tracks.get(0);

    assertEquals(1, trackDTO.id);
    assertEquals("Foo Bar", trackDTO.title);
    assertEquals("Jane Doe", trackDTO.performer);
    assertEquals(418, trackDTO.duration);
    assertEquals("Baz Lofi", trackDTO.album);
    assertEquals(69, trackDTO.playCount);
    assertEquals(date.toString(), trackDTO.publicationDate);
    assertEquals("Hello world", trackDTO.description);
    assertTrue(trackDTO.offlineAvailable);
  }
}