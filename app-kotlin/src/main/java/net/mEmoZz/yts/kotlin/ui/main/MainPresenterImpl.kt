package net.mEmoZz.yts.kotlin.ui.main

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import net.mEmoZz.yts.kotlin.data.connection.RetroConnect
import net.mEmoZz.yts.kotlin.utilities.Utils
import timber.log.Timber

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

internal class MainPresenterImpl(private var mainView: MainView?) : MainPresenter {

  private val disposables = CompositeDisposable()
  private val api = RetroConnect().initRetrofit()
  private val visibleThreshold = 5

  private var previousTotal = 0
  private var firstVisibleItem: Int = 0
  private var visibleItemCount: Int = 0
  private var totalItemCount: Int = 0
  private var currentPage = 1
  private var loading = true

  override fun onAttach() {
    mainView!!.setActionBar()
    mainView!!.setupRecycler()
    mainView!!.setupRefreshLayout()
  }

  override fun onDestroy() {
    disposables.dispose()
    mainView = null
  }

  override fun loadMoviesList(context: Context, pageNum: Int, refresh: Boolean) {
    if (Utils.isNetworkAvailable(context)) {
      if (refresh) previousTotal = 0
      api.getMoviesList(pageNum)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .doOnSubscribe {
            disposables.add(it)
            mainView!!.hideHolderViews()
            if (refresh) {
              mainView!!.showRefreshLayout()
            } else {
              showLoader(pageNum <= 1)
            }
          }.doOnComplete {
            mainView!!.enableRefreshLayout()
            if (refresh) {
              mainView!!.hideRefreshLayout()
            } else {
              hideLoader(pageNum <= 1)
            }
          }.subscribe({
            val movies = it.data!!.movies
            if (movies != null && movies.isNotEmpty()) {
              mainView!!.showRecycler()
              mainView!!.setRecyclerAdapter(movies, refresh)
            } else {
              mainView!!.showEmptyLisView()
            }
          }, {
            mainView!!.enableRefreshLayout()
            if (refresh) {
              mainView!!.hideRefreshLayout()
            } else {
              hideLoader(pageNum <= 1)
            }
            mainView!!.showErrorView()
            Timber.e(it)
          })
    } else {
      mainView!!.enableRefreshLayout()
      mainView!!.hideRecycler()
      mainView!!.hideRefreshLayout()
      mainView!!.showNoConnectionView()
    }
  }

  override fun activateEndlessScroll(recyclerView: RecyclerView, manager: GridLayoutManager) {
    RxRecyclerView.scrollEvents(recyclerView)
        .doOnSubscribe { disposables.add(it) }
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
            mainView!!.onLoadMore(currentPage)
            loading = true
          }
        }
  }

  private fun showLoader(progressBar: Boolean) {
    if (progressBar) {
      mainView!!.showProgressBar()
    } else {
      mainView!!.showLoadingBar()
    }
  }

  private fun hideLoader(progressBar: Boolean) {
    if (progressBar) {
      mainView!!.hideProgressBar()
    } else {
      mainView!!.hideLoadingBar()
    }
  }
}
