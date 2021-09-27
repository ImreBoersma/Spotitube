package nl.imreboersma.DAO;

import java.util.List;

public interface iPlaylistDAO extends iDataSource {
  List<?> getAllPlaylists(int userId);

  void deletePlaylist(int playlistId);

  void addPlaylist(int userId, String name);

  void editPlaylist(int playlistId, String name);

  List<?> getAllTracks(int playlistId);

  boolean exists(int playlistId);
}
