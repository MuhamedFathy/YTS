package net.mEmoZz.yts.java.ui.main;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import com.pnikosis.materialishprogress.ProgressWheel;
import java.util.ArrayList;
import java.util.List;
import net.mEmoZz.yts.java.R;
import net.mEmoZz.yts.java.data.models.BaseMovie;
import net.mEmoZz.yts.java.ui.base.BaseActivity;
import net.mEmoZz.yts.java.ui.main.adapters.MoviesAdapter;
import net.mEmoZz.yts.java.ui.widgets.SwipeRefreshView;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class MainActivity extends BaseActivity implements MainView {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.main_refresh_layout) SwipeRefreshView mainRefreshLayout;
  @BindView(R.id.main_progress_bar) ProgressWheel progressBar;
  @BindView(R.id.main_linear_event_holder) LinearLayout eventHolder;
  @BindView(R.id.main_iv_event_holder) ImageView eventImgHolder;
  @BindView(R.id.main_tv_event_older) TextView eventTxtHolder;
  @BindView(R.id.main_recycler_view_movies) RecyclerView moviesRecyclerList;
  @BindView(R.id.main_linear_loading_bar) LinearLayout loadingBar;

  @BindString(R.string.error_occ) String error;
  @BindString(R.string.no_data) String noData;
  @BindString(R.string.no_connection) String noConnection;

  private static final int COLUMN_COUNT = 2;

  private MainPresenter presenter;
  private MoviesAdapter adapter;
  private List<BaseMovie.Movie> moviesList = new ArrayList<>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override protected void setupPresenter() {
    presenter = new MainPresenterImpl(this);
    presenter.onAttach();
    presenter.loadMoviesList(getContext(), 1, false);
  }

  @Override protected void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }

  @Override public void setActionBar() {
    setSupportActionBar(toolbar);
  }

  @Override public void setupRecycler() {
    GridLayoutManager manager = new GridLayoutManager(getContext(), COLUMN_COUNT);
    moviesRecyclerList.setLayoutManager(manager);
    presenter.activateEndlessScroll(moviesRecyclerList, manager);
  }

  @Override public void setupRefreshLayout() {
    mainRefreshLayout.setOnRefreshListener(() -> presenter.loadMoviesList(getContext(), 1, true));
  }

  @Override public void setRecyclerAdapter(List<BaseMovie.Movie> moviesList, boolean refresh) {
    if (refresh) {
      this.moviesList.clear();
      adapter = null;
    }
    this.moviesList.addAll(moviesList);
    if (adapter == null) {
      adapter = new MoviesAdapter(getContext(), this.moviesList);
      moviesRecyclerList.setAdapter(adapter);
    } else {
      adapter.notifyItemRangeInserted(adapter.getItemCount(), this.moviesList.size() - 1);
    }
  }

  @Override public void onLoadMore(int currentPage) {
    presenter.loadMoviesList(getContext(), currentPage, false);
  }

  @Override public void enableRefreshLayout() {
    mainRefreshLayout.setEnabled(true);
  }

  @Override public void showProgressBar() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideProgressBar() {
    progressBar.setVisibility(View.GONE);
  }

  @Override public void showRefreshLayout() {
    mainRefreshLayout.setRefreshing(true);
  }

  @Override public void hideRefreshLayout() {
    mainRefreshLayout.setRefreshing(false);
  }

  @Override public void showLoadingBar() {
    loadingBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoadingBar() {
    loadingBar.setVisibility(View.GONE);
  }

  @Override public void showRecycler() {
    moviesRecyclerList.setVisibility(View.VISIBLE);
  }

  @Override public void hideRecycler() {
    moviesRecyclerList.setVisibility(View.GONE);
  }

  @Override public void showNoConnectionView() {
    showViews(R.drawable.ic_no_connection, noConnection);
  }

  @Override public void showEmptyLisView() {
    showViews(R.drawable.ic_no_movies_man, noData);
  }

  @Override public void showErrorView() {
    showViews(R.drawable.ic_error_matrix, error);
  }

  @Override public void hideHolderViews() {
    toggleEventView(false);
  }

  private void showViews(@DrawableRes int drawableId, String text) {
    eventImgHolder.setImageDrawable(getDrawable(drawableId));
    eventTxtHolder.setText(text);
    toggleEventView(true);
  }

  private void toggleEventView(boolean show) {
    eventHolder.setVisibility(show ? View.VISIBLE : View.GONE);
  }
}
