package nl.imreboersma.DAO;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistMapper implements iPlaylistDAO {
  @Resource(name = "jdbc/spotitube")
  private DataSource dataSource;

  @Override
  public void setDatasource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public List<?> getAll(int userId) {
    return null;
  }

  @Override
  public List<?> getAllPlaylists(int userId) {
    try(Connection conn = dataSource.getConnection()) {
      ResultSet result = conn.prepareStatement("SELECT * FROM playlist").executeQuery();
    } catch(SQLException throwable) {
      throwable.printStackTrace();
    }
    return new ArrayList<String>();
  }

  @Override
  public void deletePlaylist(int playlistId) {

  }

  @Override
  public void addPlaylist(int userId, String name) {

  }

  @Override
  public void editPlaylist(int playlistId, String name) {

  }

  @Override
  public List<?> getAllTracks(int playlistId) {
    return null;
  }

  @Override
  public boolean exists(int playlistId) {
    return false;
  }
}
