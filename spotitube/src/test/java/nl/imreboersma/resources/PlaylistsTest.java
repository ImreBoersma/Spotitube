package nl.imreboersma.resources;

import nl.imreboersma.DAO.iPlaylistDAO;
import nl.imreboersma.DTO.PlaylistDTO;
import nl.imreboersma.DTO.PlaylistsDTO;
import nl.imreboersma.DTO.TrackDTO;
import nl.imreboersma.domain.Playlist;
import nl.imreboersma.domain.Track;
import nl.imreboersma.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlaylistsTest {
  private static Playlists playlistsAPI;
  private static SecurityContext securityContext;
  private ArrayList<Playlist> playlistsData;
  private iPlaylistDAO playlistDAO;

  @BeforeAll
  static void setPlaylistsAPI() {
    playlistsAPI = new Playlists();
  }

  @BeforeAll
  static void mockSecurityContext() {
    securityContext = mock(SecurityContext.class);
    User user = new User();
    user.setId(1);
    when(securityContext.getUserPrincipal()).thenReturn(user);
  }

  @BeforeEach
  void generatePlaylists() {
    playlistsData = new ArrayList<>();

    Playlist ownPlaylist = new Playlist(1, "Top2000", true);
    Playlist elsesPlaylist = new Playlist(2, "Schlager", false);

    ownPlaylist.addTrack(new Track(1, "Song 1", 200));
    ownPlaylist.addTrack(new Track(2, "Song 2", 200));
    ownPlaylist.addTrack(new Track(3, "Song 3", 200));

    playlistsData.add(ownPlaylist);
    playlistsData.add(elsesPlaylist);
  }

  @BeforeEach
  void mockPlaylistDAO() {
    playlistDAO = mock(iPlaylistDAO.class);
    playlistsAPI.setPlaylistDAO(playlistDAO);
  }

  @Test
  void all() {
    when(playlistDAO.getAllPlaylistsCheckOwner(1)).thenReturn(playlistsData);

    Response response = playlistsAPI.all(securityContext);
    assertEquals(Response.Status.OK, response.getStatusInfo());

    PlaylistsDTO playlistDTO = (PlaylistsDTO) response.getEntity();
    assertNotNull(playlistDTO);
    assertEquals(600, playlistDTO.length);
    assertEquals(2, playlistDTO.playlists.size());

    TrackDTO trackDTO = playlistDTO.playlists.get(0).tracks.get(0);
    assertEquals(1, trackDTO.id);
    assertEquals("Song 1", trackDTO.title);
    assertNull(trackDTO.performer);
    assertEquals(200, trackDTO.duration);
    assertNull(trackDTO.album);
    assertEquals(0, trackDTO.playCount);
    assertEquals("", trackDTO.publicationDate);
    assertNull(trackDTO.description);
    assertFalse(trackDTO.offlineAvailable);
  }

  @Test
  void deletePlaylistDoesntExist() {
    when(playlistDAO.exists(1)).thenReturn(false);
    Response response = playlistsAPI.delete(securityContext, 1);
    assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
  }

  @Test
  void deletePlaylistExists() {
    when(playlistDAO.exists(1)).thenReturn(true);
    playlistsData.remove(0);
    when(playlistDAO.getAllPlaylistsCheckOwner(1)).thenReturn(playlistsData);

    Response response = playlistsAPI.delete(securityContext, 1);
    assertEquals(Response.Status.OK, response.getStatusInfo());

    PlaylistsDTO playlistsDTO = (PlaylistsDTO) response.getEntity();
    assertNotNull(playlistsDTO);
    assertEquals(0, playlistsDTO.length);
    assertEquals(1, playlistsDTO.playlists.size());
  }


  @Test
  void postPlaylistBadRequestTest() {
    Response response = playlistsAPI.post(securityContext, null);
    assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
  }

  @Test
  void postPlaylistSuccessTest() {
    PlaylistDTO playlistDTO = new PlaylistDTO();
    playlistDTO.name = "Death Metal 2";

    playlistsData.add(new Playlist(-1, playlistDTO.name, false));
    when(playlistDAO.getAllPlaylistsCheckOwner(1)).thenReturn(playlistsData);

    Response response = playlistsAPI.post(securityContext, playlistDTO);
    assertEquals(Response.Status.CREATED, response.getStatusInfo());

    PlaylistsDTO playlistsDTO = (PlaylistsDTO) response.getEntity();
    assertNotNull(playlistsDTO);
    assertEquals(600, playlistsDTO.length);
    assertEquals(3, playlistsDTO.playlists.size());
  }

  @Test
  void editPlaylistBadRequestTest() {
    Response response = playlistsAPI.edit(securityContext, 1, null);
    assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
  }

  @Test
  void editPlaylistNotFoundTest() {
    when(playlistDAO.exists(1)).thenReturn(false);
    Response response = playlistsAPI.edit(securityContext, 1, new PlaylistDTO());
    assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
  }

  @Test
  void editPlaylistSuccessTest() {
    when(playlistDAO.exists(1)).thenReturn(true);
    PlaylistDTO playlistDTO = new PlaylistDTO();
    playlistDTO.name = "Living metal";

    playlistsData.get(0).setName("Living metal");
    when(playlistDAO.getAllPlaylistsCheckOwner(1)).thenReturn(playlistsData);

    Response response = playlistsAPI.edit(securityContext, 1, playlistDTO);
    assertEquals(Response.Status.OK, response.getStatusInfo());

    PlaylistsDTO playlistsDTO = (PlaylistsDTO) response.getEntity();
    assertEquals(600, playlistsDTO.length);
    assertEquals(2, playlistsDTO.playlists.size());
    assertEquals("Living metal", playlistsDTO.playlists.get(0).name);
  }

}