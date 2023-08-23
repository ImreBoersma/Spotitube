package nl.imreboersma.DAO;

import org.junit.jupiter.api.Test;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrackDAOTest extends AbstractDAOTest<TrackDAO> {
  TrackDAOTest() {
    dao = new TrackDAO();
  }

  @Test
  void getTracksNotInPlaylistTest() {
    assertEquals(0, dao.getTracksNotInPlaylist(1).size());
    assertEquals(0, dao.getTracksNotInPlaylist(2).size());
  }

  @Test
  void trackExistsInPlaylistTest() {
    assertTrue(dao.trackExistsInPlaylist(1, 1));
    assertTrue(dao.trackExistsInPlaylist(1, 2));
    assertTrue(dao.trackExistsInPlaylist(1, 4));
  }

  @Test
  void addToPlaylistTest() {
    assertTrue(dao.trackExistsInPlaylist(2, 4));
    dao.addToPlaylist(2, 3, true);
    assertTrue(dao.trackExistsInPlaylist(2, 3));
  }

  @Override
  protected TrackDAO createDAOMock() {
    TrackDAO trackDAO = mock(TrackDAO.class);
    when(trackDAO.getTracksNotInPlaylist(anyInt())).thenReturn(new ArrayList<>());
    when(trackDAO.trackExistsInPlaylist(anyInt(), anyInt())).thenReturn(true);

    return trackDAO;
  }
}
