package net.mEmoZz.yts.java.data.bus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class MovieData implements Parcelable {

  private long movieId;

  private String movieName;
  private String youtubeCode;

  public MovieData(long movieId, String movieName, String youtubeCode) {
    this.movieId = movieId;
    this.movieName = movieName;
    this.youtubeCode = youtubeCode;
  }

  private MovieData(Parcel in) {
    movieId = in.readLong();
    movieName = in.readString();
    youtubeCode = in.readString();
  }

  public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
    @Override
    public MovieData createFromParcel(Parcel in) {
      return new MovieData(in);
    }

    @Override
    public MovieData[] newArray(int size) {
      return new MovieData[size];
    }
  };

  public long getMovieId() {
    return movieId;
  }

  public String getMovieName() {
    return movieName;
  }

  public String getYoutubeCode() {
    return youtubeCode;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(movieId);
    dest.writeString(movieName);
    dest.writeString(youtubeCode);
  }
}
