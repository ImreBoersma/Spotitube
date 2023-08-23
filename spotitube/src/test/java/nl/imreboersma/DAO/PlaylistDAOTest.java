package nl.imreboersma.DAO;

import nl.imreboersma.Domain.Playlist;
import nl.imreboersma.Domain.Track;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlaylistDAOTest extends AbstractDAOTest<PlaylistDAO> {
  PlaylistDAOTest() {
    dao = new PlaylistDAO();
  }

  @Test
  void getAllPlaylistsCheckOwner() {
    ArrayList<Playlist> playlists = dao.getAllPlaylistsCheckOwner(20);
    Playlist userPlaylist = playlists.get(0);
    assertTrue(userPlaylist.isOwner());
    assertTrue(playlists.get(1).isOwner());
    assertTrue(playlists.get(2).isOwner());
  }

  @Test
  void deletePlaylist() {
    dao.deletePlaylist(8);
    Optional<Playlist> maybePlaylist = dao.find(playlist -> playlist.getId() == 8);
    assertTrue(maybePlaylist.isEmpty());
  }

  @Test
  void addPlaylist() {
    dao.addPlaylist(1, "My new playlist");
    Optional<Playlist> maybePlaylist = dao.find(playlist -> Objects.equals(playlist.getName(), "My new playlist"));
    maybePlaylist.ifPresent(playlist -> assertEquals("Testing", playlist.getName()));
  }

  @Test
  void editPlaylist() {
    dao.editPlaylist(1, "Testing");
    Optional<Playlist> maybePlaylist = dao.find(playlist -> playlist.getId() == 1);
      maybePlaylist.ifPresent(playlist -> assertEquals("Testing", playlist.getName()));
  }

  @Test
  void getAllTracks() {
    ArrayList<Track> tracks = dao.getAllTracks(1);
    assertEquals(8, tracks.size());

    Track track = tracks.get(0);
    assertEquals("Superman", track.getTitle());
    track = tracks.get(1);
    assertEquals("Goon Squad", track.getTitle());
  }

  @Test
  void exists() {
    assertTrue(dao.exists(1));
    assertTrue(dao.exists(2));
  }

  @Override
  protected PlaylistDAO createDAOMock() {
    PlaylistDAO daoMock = mock(PlaylistDAO.class);
    when(daoMock.getAllPlaylistsCheckOwner(anyInt())).thenReturn(new ArrayList<>() {{
      add(new Playlist(1, "My playlist", true));
      add(new Playlist(2, "My other playlist", true));
      add(new Playlist(3, "My playlist", true));
    }});
    when(daoMock.getAllTracks(anyInt())).thenReturn(new ArrayList<>() {{
      add(new Track(1, "Superman", 2002));
      add(new Track(2, "Goon Squad", 2018));
      add(new Track(3, "The Way I Am", 2000));
      add(new Track(4, "The Real Slim Shady", 2000));
      add(new Track(5, "Stan", 2000));
      add(new Track(6, "Till I Collapse", 2002));
      add(new Track(7, "Without Me", 2002));
      add(new Track(8, "White America", 2002));
    }});
    when(daoMock.find(any())).thenReturn(Optional.empty());
    when(daoMock.exists(anyInt())).thenReturn(true);
    when(daoMock.find(any())).thenReturn(Optional.empty());

    return daoMock;
  }
}