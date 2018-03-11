package net.mEmoZz.yts.kotlin.ui.main

import android.content.Context
import io.reactivex.disposables.Disposable
import net.mEmoZz.yts.kotlin.data.models.BaseMovie
import net.mEmoZz.yts.kotlin.ui.base.BaseInteractor

/**
 * Authored by Mohamed Fathy on 11 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

interface MainInteractor : BaseInteractor {

  interface MainListener {

    fun onSubscribe(disposable: Disposable, pageNum: Int, refresh: Boolean)

    fun onComplete(pageNum: Int, refresh: Boolean)

    fun subscribe(model: BaseMovie, refresh: Boolean)

    fun onError(throwable: Throwable, pageNum: Int, refresh: Boolean)
  }

  fun loadMoviesList(context: Context, pageNum: Int, refresh: Boolean, listener: MainListener)
}
