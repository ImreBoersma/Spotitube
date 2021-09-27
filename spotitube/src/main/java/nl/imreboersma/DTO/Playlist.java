package nl.imreboersma.DTO;

import java.util.List;

public class Playlist {
  private int id;
  private String name;
  private boolean owner;
  private List<?> tracks;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getOwner() {
    return owner;
  }

  public void setOwner(boolean owner) {
    this.owner = owner;
  }

  public List<?> getTracks() {
    return tracks;
  }

  public void setTracks(List<?> tracks) {
    this.tracks = tracks;
  }
}
