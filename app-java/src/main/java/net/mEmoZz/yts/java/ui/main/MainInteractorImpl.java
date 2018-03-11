package net.mEmoZz.yts.java.ui.main;

import android.content.Context;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import net.mEmoZz.yts.java.ui.base.BaseInteractorImpl;

/**
 * Authored by Mohamed Fathy on 11 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class MainInteractorImpl extends BaseInteractorImpl implements MainInteractor {

  @Override
  public void loadMoviesList(Context context, int pageNum, boolean refresh, MainListener listener) {
    api.getMoviesList(pageNum)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(disposable -> listener.onSubscribe(disposable, pageNum, refresh))
        .doOnComplete(() -> listener.onComplete(pageNum, refresh))
        .subscribe(moviesModel -> listener.subscribe(moviesModel, refresh),
            throwable -> listener.onError(throwable, pageNum, refresh)
        );
  }
}
