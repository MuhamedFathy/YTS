package net.mEmoZz.yts.kotlin.ui.main

import android.content.Context
import net.mEmoZz.yts.kotlin.data.models.BaseMovie
import net.mEmoZz.yts.kotlin.ui.base.BasePresenter
import net.mEmoZz.yts.kotlin.ui.base.BaseView

/**
 * Authored by Mohamed Fathy on 10 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

interface MainContract {

  interface View : BaseView<Presenter> {

    fun setupRecycler()

    fun setupRefreshLayout()

    fun loadList()

    fun setRecyclerAdapter(moviesList: List<BaseMovie.Movie>, refresh: Boolean)

    fun enableRefreshLayout()

    fun showRefreshLayout()

    fun hideRefreshLayout()

    fun showLoadingBar()

    fun hideLoadingBar()

    fun showRecycler()

    fun hideRecycler()

    fun showNoConnectionView()

    fun showEmptyLisView()

    fun showErrorView()

    fun hideHolderViews()
  }

  interface Presenter : BasePresenter<View, MainInteractor> {

    fun loadMoviesList(context: Context, pageNum: Int, refresh: Boolean)
  }
}
