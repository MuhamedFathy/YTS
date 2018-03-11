package net.mEmoZz.yts.kotlin.ui.main

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.mEmoZz.yts.kotlin.ui.base.BaseInteractorImpl
import net.mEmoZz.yts.kotlin.ui.main.MainInteractor.MainListener

/**
 * Authored by Mohamed Fathy on 11 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class MainInteractorImpl : BaseInteractorImpl(), MainInteractor {

  override fun loadMoviesList(
      context: Context, pageNum: Int, refresh: Boolean, listener: MainListener
  ) {
    api.getMoviesList(pageNum)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { disposable -> listener.onSubscribe(disposable, pageNum, refresh) }
        .doOnComplete { listener.onComplete(pageNum, refresh) }
        .subscribe({ moviesModel -> listener.subscribe(moviesModel, refresh) }
        ) { throwable -> listener.onError(throwable, pageNum, refresh) }
  }
}
