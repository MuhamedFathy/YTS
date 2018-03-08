package net.mEmoZz.yts.java.data.bus;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class MovieData {

  private long movieId;

  private String movieName;
  private String youtubeCode;

  public MovieData(long movieId, String movieName, String youtubeCode) {
    this.movieId = movieId;
    this.movieName = movieName;
    this.youtubeCode = youtubeCode;
  }

  public long getMovieId() {
    return movieId;
  }

  public String getMovieName() {
    return movieName;
  }

  public String getYoutubeCode() {
    return youtubeCode;
  }
}
