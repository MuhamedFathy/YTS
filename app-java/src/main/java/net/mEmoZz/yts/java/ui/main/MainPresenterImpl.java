package net.mEmoZz.yts.java.ui.main;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import net.mEmoZz.yts.java.data.connection.RetroConnect;
import net.mEmoZz.yts.java.data.models.BaseMovie;
import net.mEmoZz.yts.java.data.webservice.BaseApi;
import net.mEmoZz.yts.java.utilities.Utils;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class MainPresenterImpl implements MainPresenter {

  private MainView mainView;
  private CompositeDisposable disposables = new CompositeDisposable();
  private BaseApi api = new RetroConnect().initRetrofit();

  private int previousTotal = 0;
  private int visibleThreshold = 5;
  private int firstVisibleItem, visibleItemCount, totalItemCount;
  private int currentPage = 1;
  private boolean loading = true;

  MainPresenterImpl(MainView mainView) {
    this.mainView = mainView;
  }

  @Override public void onAttach() {
    mainView.setActionBar();
    mainView.setupRecycler();
    mainView.setupRefreshLayout();
  }

  @Override public void onDestroy() {
    disposables.dispose();
    mainView = null;
  }

  @Override public void loadMoviesList(Context context, int pageNum, boolean refresh) {
    if (Utils.isNetworkAvailable(context)) {
      if (refresh) previousTotal = 0;
      api.getMoviesList(pageNum)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .doOnSubscribe(disposable -> {
            disposables.add(disposable);
            mainView.hideHolderViews();
            if (refresh) {
              mainView.showRefreshLayout();
            } else {
              showLoader(pageNum <= 1);
            }
          }).doOnComplete(() -> {
        mainView.enableRefreshLayout();
        if (refresh) {
          mainView.hideRefreshLayout();
        } else {
          hideLoader(pageNum <= 1);
        }
      }).subscribe(moviesModel -> {
        List<BaseMovie.Movie> movies = moviesModel.getData().getMovies();
        if (movies != null && movies.size() > 0) {
          mainView.showRecycler();
          mainView.setRecyclerAdapter(movies, refresh);
        } else {
          mainView.showEmptyLisView();
        }
      }, throwable -> {
        mainView.enableRefreshLayout();
        if (refresh) {
          mainView.hideRefreshLayout();
        } else {
          hideLoader(pageNum <= 1);
        }
        mainView.showErrorView();
      });
    } else {
      mainView.enableRefreshLayout();
      mainView.hideRecycler();
      mainView.hideRefreshLayout();
      mainView.showNoConnectionView();
    }
  }

  @Override
  public void activateEndlessScroll(RecyclerView recyclerView, GridLayoutManager manager) {
    RxRecyclerView.scrollEvents(recyclerView)
        .doOnSubscribe(disposable -> disposables.add(disposable))
        .subscribe(scrollEvent -> {
          visibleItemCount = recyclerView.getChildCount();
          totalItemCount = manager.getItemCount();
          firstVisibleItem = manager.findFirstVisibleItemPosition();
          if (loading) {
            if (totalItemCount > previousTotal) {
              loading = false;
              previousTotal = totalItemCount;
            }
          }
          if (!loading && (totalItemCount - visibleItemCount)
              <= (firstVisibleItem + visibleThreshold)) {
            currentPage++;
            mainView.onLoadMore(currentPage);
            loading = true;
          }
        });
  }

  private void showLoader(boolean progressBar) {
    if (progressBar) {
      mainView.showProgressBar();
    } else {
      mainView.showLoadingBar();
    }
  }

  private void hideLoader(boolean progressBar) {
    if (progressBar) {
      mainView.hideProgressBar();
    } else {
      mainView.hideLoadingBar();
    }
  }
}
