package net.mEmoZz.yts.kotlin.ui.detail

import android.app.Activity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.mEmoZz.yts.kotlin.ui.base.BaseInteractorImpl
import net.mEmoZz.yts.kotlin.ui.detail.DetailInteractor.DetailListener
import timber.log.Timber

/**
 * Authored by Mohamed Fathy on 10 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class DetailInteractorImpl : BaseInteractorImpl(), DetailInteractor {

  override fun loadMovieDetails(context: Activity, movieId: Long, listener: DetailListener) {
    api.getMovieDetails(movieId, true, true)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe({ listener.onDetailSubscribe(it) })
        .doOnComplete({ listener.onDetailComplete() })
        .subscribe({ detail -> listener.subscribeDetail(context, detail) }, { throwable ->
          listener.onDetailError()
          Timber.e(throwable)
        })
  }

  override fun downloadFile(url: String, listener: DetailListener) {
    api.downloadFile(url)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap({ listener.writeFileToDisk(it) })
        .doOnSubscribe({ listener.onDownloadSubscribe(it) })
        .doOnComplete({ listener.onDownloadComplete() })
        .subscribe({ listener.subscribeDownload(it) }, { listener.onDownloadError(it) })
  }
}
