package net.mEmoZz.yts.java.ui.detail;

import android.app.Activity;
import android.content.Context;
import net.mEmoZz.yts.java.data.Quality;
import net.mEmoZz.yts.java.ui.base.BasePresenter;
import net.mEmoZz.yts.java.ui.base.BaseView;
import net.mEmoZz.yts.java.ui.detail.adapters.pager.ScreenshotsAdapter;
import net.mEmoZz.yts.java.ui.detail.adapters.recycler.CastAdapter;

/**
 * Authored by Mohamed Fathy on 10 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public interface DetailContract {

  interface View extends BaseView<Presenter> {

    void receiveData();

    void setupRecycler();

    void initDialog();

    void loadDetails();

    void loadImages(String tubeUrl, String posterUrl, String backgroundUrl);

    void setInfo(String year, String genre, String rate, String description);

    void setPagerAdapter(ScreenshotsAdapter adapter);

    void setCastRecyclerAdapter(CastAdapter adapter);

    void showMainView();

    void showYear();

    void hideYear();

    void showGenres();

    void hideGenres();

    void showRate();

    void hideRate();

    void enable3D();

    void enable720p();

    void enable1080p();

    void hideSynopsis();

    void hideScreenshots();

    void hideCast();

    void showProgressDialog();

    void dismissProgressDialog();

    void showFileSavedToast();

    void showFileNotSavedToast();

    void showCopyConfirmToast();

    void showNoConnectionToast();

    void showPermissionNotGrantedToast();
  }

  interface Presenter extends BasePresenter<View, DetailInteractor> {

    void loadMovieDetails(Activity context, long movieId);

    void onPermGranted(Context context, Quality quality, boolean isGranted);

    void copyMagnet(Context context, Quality quality);

    String getMovieSize(Quality quality);
  }
}
