package net.mEmoZz.yts.kotlin.ui.main

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import net.mEmoZz.yts.kotlin.ui.base.BasePresenter

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

internal interface MainPresenter : BasePresenter {

  fun loadMoviesList(context: Context, pageNum: Int, refresh: Boolean)

  fun activateEndlessScroll(recyclerView: RecyclerView, manager: GridLayoutManager)
}
