package net.mEmoZz.yts.java.ui.detail;

import android.app.Activity;
import android.content.Context;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;
import net.mEmoZz.yts.java.data.Quality;
import net.mEmoZz.yts.java.data.Urls;
import net.mEmoZz.yts.java.data.models.MovieDetail;
import net.mEmoZz.yts.java.ui.detail.adapters.pager.ScreenshotsAdapter;
import net.mEmoZz.yts.java.ui.detail.adapters.recycler.CastAdapter;
import net.mEmoZz.yts.java.utilities.FileUtil;
import net.mEmoZz.yts.java.utilities.Utils;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class DetailPresenter implements DetailContract.Presenter, DetailInteractor.DetailListener {

  private DetailContract.View view;
  private DetailInteractor interactor;
  private CompositeDisposable disposables = new CompositeDisposable();
  private MovieDetail.Movie movie;
  private List<MovieDetail.Torrent> torrents;

  @Override public void onAttach(DetailContract.View view, DetailInteractor interactor) {
    this.view = view;
    this.interactor = interactor;
    this.view.setPresenter(this);
    this.view.setActionBar();
    this.view.setupRecycler();
    this.view.initDialog();
  }

  @Override public void onDestroy() {
    disposables.clear();
    view = null;
  }

  @Override public void loadMovieDetails(Activity context, long movieId) {
    interactor.loadMovieDetails(context, movieId, this);
  }

  @Override public void onPermGranted(Context context, Quality quality, boolean isGranted) {
    if (isGranted) {
      for (int i = 0; i < torrents.size(); i++) {
        MovieDetail.Torrent torrent = torrents.get(i);
        if (torrent.getQuality().equals(quality.value)) {
          if (Utils.isNetworkAvailable(context)) {
            interactor.downloadFile(torrent.getUrl(), this);
            break;
          } else {
            view.showNoConnectionToast();
            break;
          }
        }
      }
    } else {
      view.showPermissionNotGrantedToast();
    }
  }

  @Override public void onDetailSubscribe(Disposable disposable) {
    disposables.add(disposable);
    view.showProgressBar();
  }

  @Override public void onDownloadSubscribe(Disposable disposable) {
    disposables.add(disposable);
    view.showProgressDialog();
  }

  @Override public void onDetailComplete() {
    view.hideProgressBar();
    view.showMainView();
  }

  @Override public void onDownloadComplete() {
    view.dismissProgressDialog();
  }

  @Override public void subscribeDetail(Activity context, MovieDetail model) {
    movie = model.getData().getMovie();
    String youtubeUrl = Urls.getYouTubeImgUrl(movie.getYoutubeCode());
    view.loadImages(youtubeUrl, movie.getPoster(), movie.getBackgroundImage());
    torrents = movie.getTorrents();
    setupQuality();
    setupInfo(movie.getYear(), movie.getGenres(), movie.getRating(), movie.getDescription());
    if (Utils.isEmpty(movie.getDescription())) view.hideSynopsis();
    if (movie.getShot1() != null || movie.getShot2() != null || movie.getShot3() != null) {
      view.setPagerAdapter(
          new ScreenshotsAdapter(movie.getShot1(), movie.getShot2(), movie.getShot3())
      );
    } else {
      view.hideScreenshots();
    }
    if (movie.getCast() != null && movie.getCast().size() > 0) {
      view.setCastRecyclerAdapter(new CastAdapter(context, movie.getCast()));
    } else {
      view.hideCast();
    }
  }

  private void setupQuality() {
    if (torrents != null && torrents.size() > 0) {
      for (int i = 0; i < torrents.size(); i++) {
        MovieDetail.Torrent torrent = torrents.get(i);
        if (torrent.is3DAvailable()) {
          view.enable3D();
        } else if (torrent.is720Available()) {
          view.enable720p();
        } else if (torrent.is1080Available()) {
          view.enable1080p();
        }
      }
    }
  }

  private void setupInfo(String year, List<String> genres, String rate, String description) {
    if (!Utils.isEmpty(year)) {
      view.showYear();
    } else {
      view.hideYear();
    }
    if (genres != null && genres.size() > 0) {
      view.showGenres();
    } else {
      view.hideGenres();
    }
    if (!Utils.isEmpty(rate)) {
      view.showRate();
    } else {
      view.hideRate();
    }
    view.setInfo(year, getGenres(genres), rate, description);
  }

  private String getGenres(List<String> genres) {
    StringBuilder builder = new StringBuilder();
    if (genres != null && genres.size() > 0) {
      for (int i = 0; i < genres.size(); i++) {
        builder.append(genres.get(i));
        if (i != genres.size() - 1) builder.append(", ");
      }
      return builder.toString();
    }
    return null;
  }

  @Override public void subscribeDownload(boolean isSaved) {
    if (isSaved) {
      view.showFileSavedToast();
    } else {
      view.showFileNotSavedToast();
    }
  }

  @Override public void onDetailError() {
    view.hideProgressBar();
  }

  @Override public void onDownloadError(Throwable throwable) {
    if (!(throwable instanceof NullPointerException)) {
      view.dismissProgressDialog();
      view.showFileNotSavedToast();
    }
  }

  @Override public Observable<Boolean> writeFileToDisk(Response<ResponseBody> response) {
    return FileUtil.writeResponseToDisk(response);
  }

  @Override public void copyMagnet(Context context, Quality quality) {
    for (int i = 0; i < torrents.size(); i++) {
      MovieDetail.Torrent torrent = torrents.get(i);
      if (torrent.getQuality().equals(quality.value)) {
        Utils.copyTxt(context, Urls.buildMagnetUrl(
            torrent.getHash(), movie.getTitleLong(), quality
        ));
        view.showCopyConfirmToast();
        break;
      }
    }
  }

  @Override public String getMovieSize(Quality quality) {
    for (int i = 0; i < torrents.size(); i++) {
      MovieDetail.Torrent torrent = torrents.get(i);
      if (torrent.getQuality().equals(quality.value)) {
        return torrent.getSize();
      }
    }
    return null;
  }
}
