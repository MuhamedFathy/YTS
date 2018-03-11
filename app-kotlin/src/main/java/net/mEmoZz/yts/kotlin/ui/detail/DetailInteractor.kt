package net.mEmoZz.yts.kotlin.ui.detail

import android.app.Activity
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import net.mEmoZz.yts.kotlin.data.models.MovieDetail
import net.mEmoZz.yts.kotlin.ui.base.BaseInteractor
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Authored by Mohamed Fathy on 10 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

interface DetailInteractor : BaseInteractor {

  interface DetailListener {

    fun onDetailSubscribe(disposable: Disposable)

    fun onDownloadSubscribe(disposable: Disposable)

    fun onDetailComplete()

    fun onDownloadComplete()

    fun subscribeDetail(context: Activity, model: MovieDetail)

    fun subscribeDownload(isSaved: Boolean)

    fun onDetailError()

    fun onDownloadError(throwable: Throwable)

    fun writeFileToDisk(response: Response<ResponseBody>): Observable<Boolean>
  }

  fun loadMovieDetails(context: Activity, movieId: Long, listener: DetailListener)

  fun downloadFile(url: String, listener: DetailListener)
}
