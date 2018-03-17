package net.mEmoZz.yts.java.ui.main;

import android.content.Context;
import io.reactivex.disposables.Disposable;
import net.mEmoZz.yts.java.data.network.models.BaseMovie;
import net.mEmoZz.yts.java.ui.base.BaseInteractor;

/**
 * Authored by Mohamed Fathy on 11 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public interface MainInteractor extends BaseInteractor {

  interface MainListener {

    void onSubscribe(Disposable disposable, int pageNum, boolean refresh);

    void onComplete(int pageNum, boolean refresh);

    void subscribe(BaseMovie model, boolean refresh);

    void onError(Throwable throwable, int pageNum, boolean refresh);
  }

  void loadMoviesList(Context context, int pageNum, boolean refresh, MainListener listener);
}
