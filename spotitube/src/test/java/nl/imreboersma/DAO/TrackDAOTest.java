package nl.imreboersma.DAO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrackDAOTest extends AbstractDAOTest<TrackDAO> {
  TrackDAOTest() {
    dao = new TrackDAO();
  }

  @Test
  void getTracksNotInPlaylistTest() {
    assertEquals(4, dao.getTracksNotInPlaylist(1).size());
    assertEquals(8, dao.getTracksNotInPlaylist(2).size());
  }

  @Test
  void trackExistsInPlaylistTest() {
    assertTrue(dao.trackExistsInPlaylist(1, 1));
    assertTrue(dao.trackExistsInPlaylist(1, 2));
    assertFalse(dao.trackExistsInPlaylist(1, 4));
  }

  @Test
  void addToPlaylistTest() {
    assertFalse(dao.trackExistsInPlaylist(2, 4));
    dao.addToPlaylist(2, 3, true);
    assertTrue(dao.trackExistsInPlaylist(2, 3));
  }
}
