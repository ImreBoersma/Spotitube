package nl.imreboersma.DAO;

import nl.imreboersma.domain.Track;

import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Default
public class TrackDAO implements iTrackDAO {
  private DataSource dataSource;

  @Override
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public ArrayList<Track> getTracksNotInPlaylist(int playlistId) {
    try(Connection connection = dataSource.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT [track].* FROM [track] LEFT JOIN playlist_tracks pt on track.id = pt.track_id WHERE pt.playlist_id != ? OR pt.playlist_id IS NULL");
      statement.setInt(1, playlistId);
      ResultSet resultSet = statement.executeQuery();

      ArrayList<Track> tracks = new ArrayList<>();
      while(resultSet.next()) {
        Track track = new Track();
        track.setId(resultSet.getInt("id"));
        track.setTitle(resultSet.getString("title"));
        track.setPerformer(resultSet.getString("performer"));
        track.setDuration(resultSet.getInt("duration"));
        track.setAlbum(resultSet.getString("album"));
        track.setPlayCount(resultSet.getInt("play_count"));
        track.setPublicationDate(resultSet.getDate("publication_date"));
        track.setDescription(resultSet.getString("description"));
        tracks.add(track);
      }
      return tracks;
    } catch(SQLException throwable) {
      throwable.printStackTrace();
      throw new InternalServerErrorException();
    }
  }

  @Override
  public boolean trackExistsInPlaylist(int playlistId, int trackId) {
    try(Connection connection = dataSource.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM [playlist_tracks] pt WHERE pt.playlist_id = ? AND pt.track_id = ?");
      statement.setInt(1, playlistId);
      statement.setInt(2, trackId);
      ResultSet resultSet = statement.executeQuery();
      return resultSet.next();
    } catch(SQLException e) {
      throw new InternalServerErrorException(e);
    }
  }

  @Override
  public void deleteTrackInPlaylist(int playlistId, int trackId) {
    try(Connection connection = dataSource.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM [playlist_tracks] WHERE playlist_id = ? AND track_id = ?");
      statement.setInt(1, playlistId);
      statement.setInt(2, trackId);
      statement.executeQuery();
    } catch(SQLException e) {
      throw new InternalServerErrorException(e);
    }
  }

  @Override
  public void addToPlaylist(int playlistId, int trackId, boolean offlineAvailable) {
    try(Connection connection = dataSource.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("INSERT INTO playlist_tracks (playlist_id, track_id, offline_available) VALUES(?, ?, ?)");
      statement.setInt(1, playlistId);
      statement.setInt(2, trackId);
      statement.setBoolean(3, offlineAvailable);
      statement.executeQuery();
    } catch(SQLException e) {
      throw new InternalServerErrorException(e);
    }
  }
}
