package net.mEmoZz.yts.kotlin.data.network.models

import com.google.gson.annotations.SerializedName

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */
class BaseMovie {

  @SerializedName("status") val status: String? = null
  @SerializedName("data") val data: Data? = null

  class Torrent {

    @SerializedName("url") val url: String? = null
    @SerializedName("hash") val hash: String? = null
    @SerializedName("value") val quality: String? = null
    @SerializedName("size") val size: String? = null
    @SerializedName("date_uploaded_unix") val dateUploadedUnix: Long = 0
  }

  class Movie {

    @SerializedName("id") val id: Long = 0
    @SerializedName("url") private val url: String? = null
    @SerializedName("imdb_code") private val imdbCode: String? = null
    @SerializedName("title") val title: String? = null
    @SerializedName("year") val year: String? = null
    @SerializedName("rating") val rating: Float = 0.toFloat()
    @SerializedName("genres") val genres: List<String>? = null
    @SerializedName("description_full") val description: String? = null
    @SerializedName("yt_trailer_code") val youtubeCode: String? = null
    @SerializedName("background_image_original") val backgroundImage: String? = null
    @SerializedName("large_cover_image") val poster: String? = null
    @SerializedName("torrents") val torrents: List<Torrent>? = null
    @SerializedName("date_uploaded_unix") private val dateUploadedUnix: Int = 0
  }

  class Data {

    @SerializedName("movies") val movies: List<Movie>? = null
  }
}
