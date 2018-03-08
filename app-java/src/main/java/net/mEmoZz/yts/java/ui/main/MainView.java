package net.mEmoZz.yts.java.ui.main;

import java.util.List;
import net.mEmoZz.yts.java.data.models.BaseMovie;
import net.mEmoZz.yts.java.ui.base.BaseView;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

interface MainView extends BaseView {

  void setupRecycler();

  void setupRefreshLayout();

  void setRecyclerAdapter(List<BaseMovie.Movie> moviesList, boolean refresh);

  void onLoadMore(int currentPage);

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

