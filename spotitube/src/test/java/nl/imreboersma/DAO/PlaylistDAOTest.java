package nl.imreboersma.DAO;

import nl.imreboersma.Domain.Playlist;
import nl.imreboersma.Domain.Track;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
    assertDoesNotThrow(maybePlaylist::get);
    if(maybePlaylist.isPresent()) {
      Playlist playlist = maybePlaylist.get();
      assertEquals("My new playlist", playlist.getName());

    } else {
      fail("Playlist not found!");
    }
  }

  @Test
  void editPlaylist() {
    dao.editPlaylist(1, "Testing");
    Optional<Playlist> maybePlaylist = dao.find(playlist -> playlist.getId() == 1);
    assertDoesNotThrow(maybePlaylist::get);
    if(maybePlaylist.isPresent()) {
      Playlist playlist = maybePlaylist.get();
      assertEquals("Testing", playlist.getName());
    } else {
      fail("Playlist not found!");
    }
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

  @Test
  void doesntExist() {
    assertFalse(dao.exists(99999));
    assertFalse(dao.exists(-1));
  }
}