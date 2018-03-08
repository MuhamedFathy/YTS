package net.mEmoZz.yts.kotlin.ui.main

import net.mEmoZz.yts.kotlin.data.models.BaseMovie
import net.mEmoZz.yts.kotlin.ui.base.BaseView

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

internal interface MainView : BaseView {

  fun setupRecycler()

  fun setupRefreshLayout()

  fun setRecyclerAdapter(moviesList: List<BaseMovie.Movie>, refresh: Boolean)

  fun onLoadMore(currentPage: Int)

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

