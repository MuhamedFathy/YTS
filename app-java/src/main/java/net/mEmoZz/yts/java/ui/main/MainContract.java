package net.mEmoZz.yts.java.ui.main;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.List;
import net.mEmoZz.yts.java.data.models.BaseMovie;
import net.mEmoZz.yts.java.ui.base.BasePresenter;
import net.mEmoZz.yts.java.ui.base.BaseView;

/**
 * Authored by Mohamed Fathy on 10 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public interface MainContract {

  interface View extends BaseView<Presenter> {

    void setupRecycler();

    void setupRefreshLayout();

    void loadList();

    void setRecyclerAdapter(List<BaseMovie.Movie> moviesList, boolean refresh);

    void enableRefreshLayout();

    void showRefreshLayout();

    void hideRefreshLayout();

    void showLoadingBar();

    void hideLoadingBar();

    void showRecycler();

    void hideRecycler();

    void showNoConnectionView();

    void showEmptyLisView();

    void showErrorView();

    void hideHolderViews();
  }

  interface Presenter extends BasePresenter<View, MainInteractor> {

    void loadMoviesList(Context context, int pageNum, boolean refresh);

    void activateEndlessScroll(RecyclerView recyclerView, GridLayoutManager manager);
  }
}
