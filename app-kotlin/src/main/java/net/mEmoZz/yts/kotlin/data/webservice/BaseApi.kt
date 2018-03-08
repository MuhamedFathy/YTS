package net.mEmoZz.yts.kotlin.data.webservice

import io.reactivex.Observable
import net.mEmoZz.yts.kotlin.data.Urls.ENDPOINTS
import net.mEmoZz.yts.kotlin.data.models.BaseMovie
import net.mEmoZz.yts.kotlin.data.models.MovieDetail
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */
interface BaseApi {

  @GET(ENDPOINTS.MOVIES_LIST)
  fun getMoviesList(
      @Query("page") pageNum: Int
  ): Observable<BaseMovie>

  @GET(ENDPOINTS.MOVIE_DETAILS)
  fun getMovieDetails(
      @Query("movie_id") movieId: Long,
      @Query("with_images") withImg: Boolean,
      @Query("with_cast") withCast: Boolean
  ): Observable<MovieDetail>

  @GET fun downloadFile(@Url fileUrl: String): Observable<Response<ResponseBody>>
}
