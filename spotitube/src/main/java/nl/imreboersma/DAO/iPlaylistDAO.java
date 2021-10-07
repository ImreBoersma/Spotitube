package nl.imreboersma.DAO;

import nl.imreboersma.domain.Playlist;
import nl.imreboersma.domain.Track;

import java.util.ArrayList;

public interface iPlaylistDAO {
  ArrayList<Playlist> getAllPlaylistsCheckOwner(int userId);

  void deletePlaylist(int playlistId);

  void addPlaylist(int userId, String name);

  void editPlaylist(int playlistId, String name);

  ArrayList<Track> getAllTracks(int playlistId);

  boolean exists(int playlistId);
}
