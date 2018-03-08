package net.mEmoZz.yts.java.data.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import net.mEmoZz.yts.java.data.Quality;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class MovieDetail {

  @SerializedName("data") private Data data;
  @SerializedName("status_message") private String statusMessage;
  @SerializedName("status") private String status;

  public Data getData() {
    return data;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public String getStatus() {
    return status;
  }

  public static class Data {

    @SerializedName("movie") private Movie movie;

    public Movie getMovie() {
      return movie;
    }
  }

  public static class Movie {

    @SerializedName("date_uploaded_unix") private int dateUploadedUnix;
    @SerializedName("date_uploaded") private String dateUploaded;
    @SerializedName("torrents") private List<Torrent> torrents;
    @SerializedName("cast") private List<Cast> cast;
    @SerializedName("large_screenshot_image3") private String largeScreenshotImage3;
    @SerializedName("large_screenshot_image2") private String largeScreenshotImage2;
    @SerializedName("large_screenshot_image1") private String largeScreenshotImage1;
    @SerializedName("large_cover_image") private String largeCoverImage;
    @SerializedName("background_image_original") private String backgroundImage;
    @SerializedName("mpa_rating") private String mpaRating;
    @SerializedName("language") private String language;
    @SerializedName("yt_trailer_code") private String ytTrailerCode;
    @SerializedName("description_full") private String descriptionFull;
    @SerializedName("like_count") private int likeCount;
    @SerializedName("download_count") private int downloadCount;
    @SerializedName("genres") private List<String> genres;
    @SerializedName("runtime") private int runtime;
    @SerializedName("rating") private float rating;
    @SerializedName("year") private String year;
    @SerializedName("slug") private String slug;
    @SerializedName("title_long") private String titleLong;
    @SerializedName("title_english") private String titleEnglish;
    @SerializedName("title") private String title;
    @SerializedName("imdb_code") private String imdbCode;
    @SerializedName("url") private String url;
    @SerializedName("id") private long id;

    public int getDateUploadedUnix() {
      return dateUploadedUnix;
    }

    public String getDateUploaded() {
      return dateUploaded;
    }

    public List<Torrent> getTorrents() {
      return torrents;
    }

    public List<Cast> getCast() {
      return cast;
    }

    public String getScreenshotImage3() {
      return largeScreenshotImage3;
    }

    public String getScreenshotImage2() {
      return largeScreenshotImage2;
    }

    public String getScreenshotImage1() {
      return largeScreenshotImage1;
    }

    public String getPoster() {
      return largeCoverImage;
    }

    public String getBackgroundImage() {
      return backgroundImage;
    }

    public String getMpaRating() {
      return mpaRating;
    }

    public String getLanguage() {
      return language;
    }

    public String getYoutubeCode() {
      return ytTrailerCode;
    }

    public String getDescription() {
      return descriptionFull;
    }

    public int getLikeCount() {
      return likeCount;
    }

    public int getDownloadCount() {
      return downloadCount;
    }

    public List<String> getGenres() {
      return genres;
    }

    public int getRuntime() {
      return runtime;
    }

    public float getRating() {
      return rating;
    }

    public String getYear() {
      return year;
    }

    public String getSlug() {
      return slug;
    }

    public String getTitleLong() {
      return titleLong;
    }

    public String getTitleEnglish() {
      return titleEnglish;
    }

    public String getTitle() {
      return title;
    }

    public String getImdbCode() {
      return imdbCode;
    }

    public String getUrl() {
      return url;
    }

    public long getId() {
      return id;
    }
  }

  public static class Torrent {

    @SerializedName("date_uploaded_unix") private int dateUploadedUnix;
    @SerializedName("date_uploaded") private String dateUploaded;
    @SerializedName("size") private String size;
    @SerializedName("quality") private String quality;
    @SerializedName("hash") private String hash;
    @SerializedName("url") private String url;

    public int getDateUploadedUnix() {
      return dateUploadedUnix;
    }

    public String getDateUploaded() {
      return dateUploaded;
    }

    public String getSize() {
      return size;
    }

    public String getQuality() {
      return quality;
    }

    public String getHash() {
      return hash;
    }

    public String getUrl() {
      return url;
    }

    public boolean is3DAvailable() {
      return quality.equals(Quality.Q_3D.value);
    }

    public boolean is720Available() {
      return quality.equals(Quality.Q_720P.value);
    }

    public boolean is1080Available() {
      return quality.equals(Quality.Q_1080P.value);
    }
  }

  public static class Cast {

    @SerializedName("imdb_code") private String imdbCode;
    @SerializedName("url_small_image") private String urlSmallImage;
    @SerializedName("character_name") private String characterName;
    @SerializedName("name") private String name;

    public String getImdbCode() {
      return imdbCode;
    }

    public String getUrlSmallImage() {
      return urlSmallImage;
    }

    public String getCharacterName() {
      return characterName;
    }

    public String getName() {
      return name;
    }
  }
}
