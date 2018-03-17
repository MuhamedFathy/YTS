package net.mEmoZz.yts.kotlin.ui.main

import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import net.mEmoZz.yts.kotlin.data.network.models.BaseMovie
import net.mEmoZz.yts.kotlin.utilities.Utils
import timber.log.Timber

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

internal class MainPresenter : MainContract.Presenter, MainInteractor.MainListener {

  private var view: MainContract.View? = null
  private var interactor: MainInteractor? = null
  private val disposables = CompositeDisposable()

  override fun onAttach(view: MainContract.View, interactor: MainInteractor) {
    this.view = view
    this.interactor = interactor
    this.view!!.setPresenter(this)
    this.view!!.setActionBar()
    this.view!!.setupRecycler()
    this.view!!.setupRefreshLayout()
    this.view!!.loadList()
  }

  override fun onDestroy() {
    disposables.clear()
    view = null
  }

  override fun loadMoviesList(context: Context, pageNum: Int, refresh: Boolean) {
    if (Utils.isNetworkAvailable(context)) {
      interactor!!.loadMoviesList(context, pageNum, refresh, this)
    } else {
      controlNoConnection()
    }
  }

  override fun onSubscribe(disposable: Disposable, pageNum: Int, refresh: Boolean) {
    disposables.add(disposable)
    view!!.hideHolderViews()
    if (refresh) {
      view!!.showRefreshLayout()
    } else {
      showLoader(pageNum <= 1)
    }
  }

  override fun onComplete(pageNum: Int, refresh: Boolean) {
    view!!.enableRefreshLayout()
    if (refresh) {
      view!!.hideRefreshLayout()
    } else {
      hideLoader(pageNum <= 1)
    }
  }

  override fun subscribe(model: BaseMovie, refresh: Boolean) {
    val movies = model.data!!.movies
    if (movies != null && movies.isNotEmpty()) {
      view!!.showRecycler()
      view!!.setRecyclerAdapter(movies, refresh)
    } else {
      view!!.showEmptyLisView()
    }
  }

  override fun onError(throwable: Throwable, pageNum: Int, refresh: Boolean) {
    view!!.enableRefreshLayout()
    if (refresh) {
      view!!.hideRefreshLayout()
    } else {
      hideLoader(pageNum <= 1)
    }
    view!!.showErrorView()
    Timber.e(throwable)
  }

  private fun controlNoConnection() {
    view!!.enableRefreshLayout()
    view!!.hideRecycler()
    view!!.hideRefreshLayout()
    view!!.showNoConnectionView()
  }

  private fun showLoader(progressBar: Boolean) {
    if (progressBar) {
      view!!.showProgressBar()
    } else {
      view!!.showLoadingBar()
    }
  }

  private fun hideLoader(progressBar: Boolean) {
    if (progressBar) {
      view!!.hideProgressBar()
    } else {
      view!!.hideLoadingBar()
    }
  }
}
