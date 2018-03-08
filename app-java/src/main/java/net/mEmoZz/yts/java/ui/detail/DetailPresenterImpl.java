package net.mEmoZz.yts.java.ui.detail;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import net.mEmoZz.yts.java.data.Quality;
import net.mEmoZz.yts.java.data.Urls;
import net.mEmoZz.yts.java.data.connection.RetroConnect;
import net.mEmoZz.yts.java.data.models.MovieDetail;
import net.mEmoZz.yts.java.data.webservice.BaseApi;
import net.mEmoZz.yts.java.ui.detail.adapters.pager.ScreenshotsAdapter;
import net.mEmoZz.yts.java.ui.detail.adapters.recycler.CastAdapter;
import net.mEmoZz.yts.java.utilities.FileUtil;
import net.mEmoZz.yts.java.utilities.Utils;
import timber.log.Timber;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class DetailPresenterImpl implements DetailPresenter {

  private DetailView detailView;
  private CompositeDisposable disposables = new CompositeDisposable();
  private MovieDetail.Movie movie;
  private List<MovieDetail.Torrent> torrents;
  private BaseApi api = new RetroConnect().initRetrofit();

  DetailPresenterImpl(DetailView detailView) {
    this.detailView = detailView;
  }

  @Override public void onAttach() {
    detailView.setActionBar();
    detailView.setupRecycler();
    detailView.initDialog();
  }

  @Override public void onDestroy() {
    disposables.dispose();
    detailView = null;
  }

  @Override public void loadMovieDetails(Activity context, long movieId) {
    if (Utils.isNetworkAvailable(context)) {
      api.getMovieDetails(movieId, true, true)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .doOnSubscribe(disposable -> {
            disposables.add(disposable);
            detailView.showProgressBar();
          }).doOnComplete(() -> {
        detailView.hideProgressBar();
        detailView.showMainView();
      }).subscribe(movieDetail -> {
        movie = movieDetail.getData().getMovie();
        String youtubeUrl = Urls.getYouTubeImgUrl(movie.getYoutubeCode());
        detailView.loadImages(youtubeUrl, movie.getPoster(), movie.getBackgroundImage());
        torrents = movie.getTorrents();
        setupQuality();
        setupInfo(
            movie.getYear(),
            movie.getGenres(),
            String.valueOf(movie.getRating()),
            movie.getDescription()
        );
        if (Utils.isEmpty(movie.getDescription())) detailView.hideSynopsis();
        if (isScreenshotsAvailable()) {
          detailView.setPagerAdapter(new ScreenshotsAdapter(
              movie.getScreenshotImage1(),
              movie.getScreenshotImage2(),
              movie.getScreenshotImage3()
          ));
        } else {
          detailView.hideScreenshots();
        }
        if (movie.getCast() != null && movie.getCast().size() > 0) {
          detailView.setCastRecyclerAdapter(new CastAdapter(context, movie.getCast()));
        } else {
          detailView.hideCast();
        }
      }, Timber::e);
    }
  }

  private boolean isScreenshotsAvailable() {
    return movie.getScreenshotImage1() != null
        || movie.getScreenshotImage2() != null
        || movie.getScreenshotImage3() != null;
  }

  @Override public void downloadFile(Activity context, Quality quality) {
    RxPermissions rxPermissions = new RxPermissions(context);
    rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .doOnSubscribe(disposable -> disposables.add(disposable))
        .subscribe(granted -> {
          if (granted) {
            for (int i = 0; i < torrents.size(); i++) {
              MovieDetail.Torrent torrent = torrents.get(i);
              if (torrent.getQuality().equals(quality.value)) {
                if (Utils.isNetworkAvailable(context)) {
                  api.downloadFile(torrent.getUrl())
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe(disposable -> {
                        disposables.add(disposable);
                        detailView.showProgressDialog();
                      })
                      .doOnComplete(() -> detailView.dismissProgressDialog())
                      .flatMap(FileUtil::writeResponseToDisk)
                      .subscribe(file -> detailView.showFileSavedToast(), throwable -> {
                        detailView.dismissProgressDialog();
                        detailView.showFileNotSavedToast();
                        Timber.e(throwable);
                      });
                } else {
                  detailView.showNoConnectionToast();
                }
                break;
              }
            }
          } else {
            detailView.showPermissionNotGrantedToast();
          }
        });
  }

  @Override public void copyMagnet(Context context, Quality quality) {
    for (int i = 0; i < torrents.size(); i++) {
      MovieDetail.Torrent torrent = torrents.get(i);
      if (torrent.getQuality().equals(quality.value)) {
        Utils.copyTxt(context, Urls.buildMagnetUrl(
            torrent.getHash(), movie.getTitleLong(), quality
        ));
        detailView.showCopyConfirmToast();
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

  private void setupQuality() {
    if (torrents != null && torrents.size() > 0) {
      for (int i = 0; i < torrents.size(); i++) {
        MovieDetail.Torrent torrent = torrents.get(i);
        if (torrent.is3DAvailable()) {
          detailView.enable3D();
        } else if (torrent.is720Available()) {
          detailView.enable720p();
        } else if (torrent.is1080Available()) {
          detailView.enable1080p();
        }
      }
    }
  }

  private void setupInfo(String year, List<String> genres, String rate, String description) {
    if (!Utils.isEmpty(year)) {
      detailView.showYear();
    } else {
      detailView.hideYear();
    }
    if (genres != null && genres.size() > 0) {
      detailView.showGenres();
    } else {
      detailView.hideGenres();
    }
    if (!Utils.isEmpty(rate)) {
      detailView.showRate();
    } else {
      detailView.hideRate();
    }
    detailView.setInfo(year, getGenres(genres), rate, description);
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
}
