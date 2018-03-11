package net.mEmoZz.yts.java.ui.main;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;
import net.mEmoZz.yts.java.data.models.BaseMovie;
import net.mEmoZz.yts.java.utilities.Utils;
import timber.log.Timber;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class MainPresenter implements MainContract.Presenter, MainInteractor.MainListener {

  private MainContract.View view;
  private MainInteractor interactor;
  private CompositeDisposable disposables = new CompositeDisposable();

  @Override public void onAttach(MainContract.View view, MainInteractor interactor) {
    this.view = view;
    this.interactor = interactor;
    this.view.setPresenter(this);
    this.view.setActionBar();
    this.view.setupRecycler();
    this.view.setupRefreshLayout();
    this.view.loadList();
  }

  @Override public void onDestroy() {
    disposables.clear();
    view = null;
  }

  @Override public void loadMoviesList(Context context, int pageNum, boolean refresh) {
    if (Utils.isNetworkAvailable(context)) {
      interactor.loadMoviesList(context, pageNum, refresh, this);
    } else {
      controlNoConnection();
    }
  }

  @Override public void onSubscribe(Disposable disposable, int pageNum, boolean refresh) {
    disposables.add(disposable);
    view.hideHolderViews();
    if (refresh) {
      view.showRefreshLayout();
    } else {
      showLoader(pageNum <= 1);
    }
  }

  @Override public void onComplete(int pageNum, boolean refresh) {
    view.enableRefreshLayout();
    if (refresh) {
      view.hideRefreshLayout();
    } else {
      hideLoader(pageNum <= 1);
    }
  }

  @Override public void subscribe(BaseMovie model, boolean refresh) {
    List<BaseMovie.Movie> movies = model.getData().getMovies();
    if (movies != null && movies.size() > 0) {
      view.showRecycler();
      view.setRecyclerAdapter(movies, refresh);
    } else {
      view.showEmptyLisView();
    }
  }

  @Override public void onError(Throwable throwable, int pageNum, boolean refresh) {
    view.enableRefreshLayout();
    if (refresh) {
      view.hideRefreshLayout();
    } else {
      hideLoader(pageNum <= 1);
    }
    view.showErrorView();
    Timber.e(throwable);
  }

  private void controlNoConnection() {
    view.enableRefreshLayout();
    view.hideRecycler();
    view.hideRefreshLayout();
    view.showNoConnectionView();
  }

  @Override
  public void activateEndlessScroll(RecyclerView recyclerView, GridLayoutManager manager) {

  }

  private void showLoader(boolean progressBar) {
    if (progressBar) {
      view.showProgressBar();
    } else {
      view.showLoadingBar();
    }
  }

  private void hideLoader(boolean progressBar) {
    if (progressBar) {
      view.hideProgressBar();
    } else {
      view.hideLoadingBar();
    }
  }
}
