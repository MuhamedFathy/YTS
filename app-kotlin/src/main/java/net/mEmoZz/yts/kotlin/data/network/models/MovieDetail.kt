package net.mEmoZz.yts.kotlin.data.network.models

import com.google.gson.annotations.SerializedName
import net.mEmoZz.yts.kotlin.data.Quality

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */
class MovieDetail {

  @SerializedName("data") val data: Data? = null
  @SerializedName("status_message") val statusMessage: String? = null
  @SerializedName("status") val status: String? = null

  class Data {

    @SerializedName("movie") val movie: Movie? = null
  }

  class Movie {

    @SerializedName("date_uploaded_unix") val dateUploadedUnix: Int = 0
    @SerializedName("date_uploaded") val dateUploaded: String? = null
    @SerializedName("torrents") val torrents: List<Torrent>? = null
    @SerializedName("cast") val cast: List<Cast>? = null
    @SerializedName("large_screenshot_image3") val shot3: String? = null
    @SerializedName("large_screenshot_image2") val shot2: String? = null
    @SerializedName("large_screenshot_image1") val shot1: String? = null
    @SerializedName("large_cover_image") val poster: String? = null
    @SerializedName("background_image_original") val backgroundImage: String? = null
    @SerializedName("mpa_rating") val mpaRating: String? = null
    @SerializedName("language") val language: String? = null
    @SerializedName("yt_trailer_code") val youtubeCode: String? = null
    @SerializedName("description_full") val description: String? = null
    @SerializedName("like_count") val likeCount: Int = 0
    @SerializedName("download_count") val downloadCount: Int = 0
    @SerializedName("genres") val genres: List<String>? = null
    @SerializedName("runtime") val runtime: Int = 0
    @SerializedName("rating") val rating: Float = 0.toFloat()
    @SerializedName("year") val year: String? = null
    @SerializedName("slug") val slug: String? = null
    @SerializedName("title_long") val titleLong: String? = null
    @SerializedName("title_english") val titleEnglish: String? = null
    @SerializedName("title") val title: String? = null
    @SerializedName("imdb_code") val imdbCode: String? = null
    @SerializedName("url") val url: String? = null
    @SerializedName("id") val id: Long = 0
  }

  class Torrent {

    @SerializedName("date_uploaded_unix") val dateUploadedUnix: Int = 0
    @SerializedName("date_uploaded") val dateUploaded: String? = null
    @SerializedName("size") val size: String? = null
    @SerializedName("quality") val quality: String? = null
    @SerializedName("hash") val hash: String? = null
    @SerializedName("url") val url: String? = null

    val is3DAvailable: Boolean get() = quality.equals(Quality.Q_3D.value)
    val is720Available: Boolean get() = quality.equals(Quality.Q_720P.value)
    val is1080Available: Boolean get() = quality.equals(Quality.Q_1080P.value)
  }

  class Cast {

    @SerializedName("imdb_code") val imdbCode: String? = null
    @SerializedName("url_small_image") val urlSmallImage: String? = null
    @SerializedName("character_name") val characterName: String? = null
    @SerializedName("name") val name: String? = null
  }
}
