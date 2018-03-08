package net.mEmoZz.yts.java.data.webservice;

import io.reactivex.Observable;
import net.mEmoZz.yts.java.data.Urls.ENDPOINTS;
import net.mEmoZz.yts.java.data.models.BaseMovie;
import net.mEmoZz.yts.java.data.models.MovieDetail;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public interface BaseApi {

  @GET(ENDPOINTS.MOVIES_LIST) Observable<BaseMovie> getMoviesList(
      @Query("page") int pageNum
  );

  @GET(ENDPOINTS.MOVIE_DETAILS) Observable<MovieDetail> getMovieDetails(
      @Query("movie_id") long movieId,
      @Query("with_images") boolean withImg,
      @Query("with_cast") boolean withCast
  );

  @GET Observable<Response<ResponseBody>> downloadFile(@Url String fileUrl);
}
