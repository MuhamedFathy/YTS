package net.mEmoZz.yts.kotlin.ui.detail

import android.app.Activity
import android.content.Context
import net.mEmoZz.yts.kotlin.data.Quality
import net.mEmoZz.yts.kotlin.ui.base.BasePresenter

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

interface DetailPresenter : BasePresenter {

  fun loadMovieDetails(context: Activity, movieId: Long)

  fun downloadFile(context: Activity, quality: Quality)

  fun copyMagnet(context: Context, quality: Quality)

  fun getMovieSize(quality: Quality): String?
}
