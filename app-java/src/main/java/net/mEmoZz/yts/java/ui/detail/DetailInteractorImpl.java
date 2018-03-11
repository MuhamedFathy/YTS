package net.mEmoZz.yts.java.ui.detail;

import android.app.Activity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import net.mEmoZz.yts.java.ui.base.BaseInteractorImpl;
import timber.log.Timber;

/**
 * Authored by Mohamed Fathy on 10 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class DetailInteractorImpl extends BaseInteractorImpl implements DetailInteractor {

  @Override public void loadMovieDetails(Activity context, long movieId, DetailListener listener) {
    api.getMovieDetails(movieId, true, true)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(listener::onDetailSubscribe)
        .doOnComplete(listener::onDetailComplete)
        .subscribe(detail -> listener.subscribeDetail(context, detail), throwable -> {
          listener.onDetailError();
          Timber.e(throwable);
        });
  }

  @Override public void downloadFile(String url, DetailListener listener) {
    api.downloadFile(url)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap(listener::writeFileToDisk)
        .doOnSubscribe(listener::onDownloadSubscribe)
        .doOnComplete(listener::onDownloadComplete)
        .subscribe(listener::subscribeDownload, listener::onDownloadError);
  }
}
