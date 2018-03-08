package net.mEmoZz.yts.java.data.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class BaseMovie {

  @SerializedName("status") private String status;
  @SerializedName("data") private Data data;

  public String getStatus() {
    return status;
  }

  public Data getData() {
    return data;
  }

  public static class Torrent {

    @SerializedName("url") private String url;
    @SerializedName("hash") private String hash;
    @SerializedName("value") private String quality;
    @SerializedName("size") private String size;
    @SerializedName("date_uploaded_unix") private long dateUploadedUnix;

    public String getUrl() {
      return url;
    }

    public String getHash() {
      return hash;
    }

    public String getQuality() {
      return quality;
    }

    public String getSize() {
      return size;
    }

    public long getDateUploadedUnix() {
      return dateUploadedUnix;
    }
  }

  public static class Movie {

    @SerializedName("id") private long id;
    @SerializedName("url") private String url;
    @SerializedName("imdb_code") private String imdbCode;
    @SerializedName("title") private String title;
    @SerializedName("year") private long year;
    @SerializedName("rating") private float rating;
    @SerializedName("genres") private List<String> genres;
    @SerializedName("description_full") private String descriptionFull;
    @SerializedName("yt_trailer_code") private String ytTrailerCode;
    @SerializedName("background_image_original") private String backgroundImage;
    @SerializedName("large_cover_image") private String largeCoverImage;
    @SerializedName("torrents") private List<Torrent> torrents;
    @SerializedName("date_uploaded_unix") private int dateUploadedUnix;

    public long getId() {
      return id;
    }

    public String getTitle() {
      return title;
    }

    public String getDescription() {
      return descriptionFull;
    }

    public String getYear() {
      return String.valueOf(year);
    }

    public float getRating() {
      return rating;
    }

    public String getBackgroundImage() {
      return backgroundImage;
    }

    public String getPoster() {
      return largeCoverImage;
    }

    public String getYoutubeCode() {
      return ytTrailerCode;
    }

    public List<String> getGenres() {
      return genres;
    }

    public List<Torrent> getTorrents() {
      return torrents;
    }
  }

  public static class Data {

    @SerializedName("movies") private List<Movie> movies;

    public List<Movie> getMovies() {
      return movies;
    }
  }
}
