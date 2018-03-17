package net.mEmoZz.yts.java.ui.detail;

import android.app.Activity;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import net.mEmoZz.yts.java.data.network.models.MovieDetail;
import net.mEmoZz.yts.java.ui.base.BaseInteractor;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Authored by Mohamed Fathy on 10 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public interface DetailInteractor extends BaseInteractor {

  interface DetailListener {

    void onDetailSubscribe(Disposable disposable);

    void onDownloadSubscribe(Disposable disposable);

    void onDetailComplete();

    void onDownloadComplete();

    void subscribeDetail(Activity context, MovieDetail model);

    void subscribeDownload(boolean isSaved);

    void onDetailError();

    void onDownloadError(Throwable throwable);

    Observable<Boolean> writeFileToDisk(Response<ResponseBody> response);
  }

  void loadMovieDetails(Activity context, long movieId, DetailListener listener);

  void downloadFile(String url, DetailListener listener);
}
