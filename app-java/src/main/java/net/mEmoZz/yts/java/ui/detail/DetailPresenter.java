package net.mEmoZz.yts.java.ui.detail;

import android.app.Activity;
import android.content.Context;
import net.mEmoZz.yts.java.data.Quality;
import net.mEmoZz.yts.java.ui.base.BasePresenter;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public interface DetailPresenter extends BasePresenter {

  void loadMovieDetails(Activity context, long movieId);

  void downloadFile(Activity context, Quality quality);

  void copyMagnet(Context context, Quality quality);

  String getMovieSize(Quality quality);
}
