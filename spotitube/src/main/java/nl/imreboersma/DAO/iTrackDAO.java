package nl.imreboersma.DAO;

import nl.imreboersma.domain.Track;

import java.util.ArrayList;

public interface iTrackDAO extends iDAO {
  ArrayList<Track> getTracksNotInPlaylist(int playlistId);

  boolean trackExistsInPlaylist(int playlistId, int trackId);

  void deleteTrackInPlaylist(int playlistId, int trackId);

  void addToPlaylist(int playlistId, int trackId, boolean offlineAvailable);

}
