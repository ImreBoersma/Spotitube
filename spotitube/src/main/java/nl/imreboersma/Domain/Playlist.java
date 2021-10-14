package nl.imreboersma.Domain;

import java.util.ArrayList;

public class Playlist {
  private ArrayList<Track> tracks = new ArrayList<>();
  private int id;
  private String name;
  private boolean owner;

  public Playlist(int id, String name, boolean owner) {
    this.id = id;
    this.name = name;
    this.owner = owner;
  }

  public Playlist() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getLength() {
    return tracks.stream().mapToInt(Track::getDuration).sum();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isOwner() {
    return owner;
  }

  public void setOwner(boolean owner) {
    this.owner = owner;
  }

  public ArrayList<Track> getTracks() {
    return tracks;
  }

  public void setTracks(ArrayList<Track> tracks) {
    this.tracks = tracks;
  }

  public void addTrack(Track track) {
    tracks.add(track);
  }
}
