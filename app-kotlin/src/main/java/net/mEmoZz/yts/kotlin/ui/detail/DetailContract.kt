package net.mEmoZz.yts.kotlin.ui.detail

import android.app.Activity
import android.content.Context
import net.mEmoZz.yts.kotlin.data.Quality
import net.mEmoZz.yts.kotlin.ui.base.BasePresenter
import net.mEmoZz.yts.kotlin.ui.base.BaseView
import net.mEmoZz.yts.kotlin.ui.detail.adapters.pager.ScreenshotsAdapter
import net.mEmoZz.yts.kotlin.ui.detail.adapters.recycler.CastAdapter

/**
 * Authored by Mohamed Fathy on 10 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

interface DetailContract {

  interface View : BaseView<Presenter> {

    fun setupRecycler()

    fun initDialog()

    fun loadImages(tubeUrl: String, posterUrl: String, backgroundUrl: String)

    fun setInfo(year: String, genre: String, rate: String, description: String)

    fun setPagerAdapter(adapter: ScreenshotsAdapter)

    fun setCastRecyclerAdapter(adapter: CastAdapter)

    fun showMainView()

    fun showYear()

    fun hideYear()

    fun showGenres()

    fun hideGenres()

    fun showRate()

    fun hideRate()

    fun enable3D()

    fun enable720p()

    fun enable1080p()

    fun hideSynopsis()

    fun hideScreenshots()

    fun hideCast()

    fun showProgressDialog()

    fun dismissProgressDialog()

    fun showFileSavedToast()

    fun showFileNotSavedToast()

    fun showCopyConfirmToast()

    fun showNoConnectionToast()

    fun showPermissionNotGrantedToast()

    fun initClicks()
  }

  interface Presenter : BasePresenter<View, DetailInteractor> {

    fun loadMovieDetails(context: Activity, movieId: Long)

    fun onPermGranted(context: Context, quality: Quality, isGranted: Boolean)

    fun copyMagnet(context: Context, quality: Quality)

    fun getMovieSize(quality: Quality): String?
  }
}
