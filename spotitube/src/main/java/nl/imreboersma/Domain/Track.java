package nl.imreboersma.Domain;

import java.util.Date;

public class Track {
  private int id;
  private String title;
  private String performer;
  private int duration;
  private String album;
  private int playCount;
  private Date publicationDate;
  private String description;
  private boolean offlineAvailable;

  public Track() {
  }

  public Track(int id, String title, int duration) {
    this.id = id;
    this.title = title;
    this.duration = duration;
  }

  public Track(int id, String title, String performer, int duration, String album, int playCount, Date publicationDate, String description, boolean offlineAvailable) {
    this.id = id;
    this.title = title;
    this.performer = performer;
    this.duration = duration;
    this.album = album;
    this.playCount = playCount;
    this.publicationDate = publicationDate;
    this.description = description;
    this.offlineAvailable = offlineAvailable;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPerformer() {
    return performer;
  }

  public void setPerformer(String performer) {
    this.performer = performer;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public int getPlayCount() {
    return playCount;
  }

  public void setPlayCount(int playCount) {
    this.playCount = playCount;
  }

  public Date getPublicationDate() {
    return publicationDate;
  }

  public void setPublicationDate(Date publicationDate) {
    this.publicationDate = publicationDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isOfflineAvailable() {
    return offlineAvailable;
  }

  public void setOfflineAvailable(boolean offlineAvailable) {
    this.offlineAvailable = offlineAvailable;
  }
}
