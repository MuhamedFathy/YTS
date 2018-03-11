package net.mEmoZz.yts.kotlin.ui.main

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.content_main.main_iv_event_holder
import kotlinx.android.synthetic.main.content_main.main_linear_event_holder
import kotlinx.android.synthetic.main.content_main.main_linear_loading_bar
import kotlinx.android.synthetic.main.content_main.main_progress_bar
import kotlinx.android.synthetic.main.content_main.main_recycler_view_movies
import kotlinx.android.synthetic.main.content_main.main_refresh_layout
import kotlinx.android.synthetic.main.content_main.main_tv_event_older
import net.mEmoZz.yts.kotlin.R
import net.mEmoZz.yts.kotlin.data.models.BaseMovie
import net.mEmoZz.yts.kotlin.ui.base.BaseActivity
import net.mEmoZz.yts.kotlin.ui.main.MainContract.Presenter
import net.mEmoZz.yts.kotlin.ui.main.adapters.MoviesAdapter
import java.util.ArrayList

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class MainActivity : BaseActivity(), MainContract.View {

  private var presenter: Presenter? = null
  private var adapter: MoviesAdapter? = null

  private val moviesList = ArrayList<BaseMovie.Movie>()

  private var previousTotal = 0
  private val visibleThreshold = 5
  private var firstVisibleItem: Int = 0
  private var visibleItemCount: Int = 0
  private var totalItemCount: Int = 0
  private var currentPage = 1
  private var loading = true

  companion object {
    private const val COLUMN_COUNT = 2
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun initPresenter() {
    MainPresenter().onAttach(this, MainInteractorImpl())
  }

  override fun onDestroy() {
    presenter!!.onDestroy()
    super.onDestroy()
  }

  override fun setPresenter(presenter: MainContract.Presenter) {
    this.presenter = presenter
  }

  override fun setActionBar() {
    setSupportActionBar(toolbar)
  }

  override fun setupRecycler() {
    val manager = GridLayoutManager(context, COLUMN_COUNT)
    main_recycler_view_movies.layoutManager = manager
    activateEndlessScroll(main_recycler_view_movies, manager)
  }

  private fun activateEndlessScroll(recyclerView: RecyclerView, manager: GridLayoutManager) {
    RxRecyclerView.scrollEvents(recyclerView)
        .subscribe {
          visibleItemCount = recyclerView.childCount
          totalItemCount = manager.itemCount
          firstVisibleItem = manager.findFirstVisibleItemPosition()
          if (loading) {
            if (totalItemCount > previousTotal) {
              loading = false
              previousTotal = totalItemCount
            }
          }
          if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            currentPage++
            presenter!!.loadMoviesList(context!!, currentPage, false)
            loading = true
          }
        }
  }

  override fun setupRefreshLayout() {
    main_refresh_layout.setOnRefreshListener {
      previousTotal = 0
      presenter!!.loadMoviesList(this, 1, true)
    }
  }

  override fun loadList() {
    presenter!!.loadMoviesList(context!!, 1, false)
  }

  override fun setRecyclerAdapter(moviesList: List<BaseMovie.Movie>, refresh: Boolean) {
    if (refresh) {
      this.moviesList.clear()
      adapter = null
    }
    this.moviesList.addAll(moviesList)
    if (adapter == null) {
      adapter = MoviesAdapter(context!!, this.moviesList)
      main_recycler_view_movies!!.adapter = adapter
    } else {
      adapter!!.notifyItemRangeInserted(adapter!!.itemCount, this.moviesList.size - 1)
    }
  }

  override fun enableRefreshLayout() {
    main_refresh_layout.isEnabled = true
  }

  override fun showProgressBar() {
    main_progress_bar.visibility = View.VISIBLE
  }

  override fun hideProgressBar() {
    main_progress_bar.visibility = View.GONE
  }

  override fun showRefreshLayout() {
    main_refresh_layout.isRefreshing = true
  }

  override fun hideRefreshLayout() {
    main_refresh_layout.isRefreshing = false
  }

  override fun showLoadingBar() {
    main_linear_loading_bar.visibility = View.VISIBLE
  }

  override fun hideLoadingBar() {
    main_linear_loading_bar.visibility = View.GONE
  }

  override fun showRecycler() {
    main_recycler_view_movies.visibility = View.VISIBLE
  }

  override fun hideRecycler() {
    main_recycler_view_movies.visibility = View.GONE
  }

  override fun showNoConnectionView() {
    showViews(R.drawable.ic_no_connection, getString(R.string.no_connection))
  }

  override fun showEmptyLisView() {
    showViews(R.drawable.ic_no_movies_man, getString(R.string.no_data))
  }

  override fun showErrorView() {
    showViews(R.drawable.ic_error_matrix, getString(R.string.error_occ))
  }

  override fun hideHolderViews() {
    toggleEventView(false)
  }

  private fun showViews(@DrawableRes drawableId: Int, text: String) {
    main_iv_event_holder.setImageDrawable(getDrawable(drawableId))
    main_tv_event_older.text = text
    toggleEventView(true)
  }

  private fun toggleEventView(show: Boolean) {
    main_linear_event_holder.visibility = if (show) View.VISIBLE else View.GONE
  }
}
