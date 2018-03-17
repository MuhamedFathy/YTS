package net.mEmoZz.yts.kotlin.ui.detail

import android.app.Activity
import android.content.Context
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import net.mEmoZz.yts.kotlin.data.Quality
import net.mEmoZz.yts.kotlin.data.network.Urls
import net.mEmoZz.yts.kotlin.data.network.models.MovieDetail
import net.mEmoZz.yts.kotlin.ui.detail.adapters.pager.ScreenshotsAdapter
import net.mEmoZz.yts.kotlin.ui.detail.adapters.recycler.CastAdapter
import net.mEmoZz.yts.kotlin.utilities.FileUtil
import net.mEmoZz.yts.kotlin.utilities.Utils
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class DetailPresenter : DetailContract.Presenter, DetailInteractor.DetailListener {

  private var view: DetailContract.View? = null
  private var interactor: DetailInteractor? = null
  private val disposables = CompositeDisposable()
  private var movie: MovieDetail.Movie? = null
  private var torrents: List<MovieDetail.Torrent>? = null

  override fun onAttach(view: DetailContract.View, interactor: DetailInteractor) {
    this.view = view
    this.interactor = interactor
    this.view!!.setPresenter(this)
    this.view!!.setActionBar()
    this.view!!.setupRecycler()
    this.view!!.initDialog()
    this.view!!.initClicks()
  }

  override fun onDestroy() {
    disposables.clear()
    view = null
  }

  override fun loadMovieDetails(context: Activity, movieId: Long) {
    interactor!!.loadMovieDetails(context, movieId, this)
  }

  override fun onPermGranted(context: Context, quality: Quality, isGranted: Boolean) {
    if (isGranted) {
      for (i in torrents!!.indices) {
        val torrent = torrents!![i]
        if (torrent.quality == quality.value) {
          if (Utils.isNetworkAvailable(context)) {
            interactor!!.downloadFile(torrent.url!!, this)
            break
          } else {
            view!!.showNoConnectionToast()
            break
          }
        }
      }
    } else {
      view!!.showPermissionNotGrantedToast()
    }
  }

  override fun onDetailSubscribe(disposable: Disposable) {
    disposables.add(disposable)
    view!!.showProgressBar()
  }

  override fun onDownloadSubscribe(disposable: Disposable) {
    disposables.add(disposable)
    view!!.showProgressDialog()
  }

  override fun onDetailComplete() {
    view!!.hideProgressBar()
    view!!.showMainView()
  }

  override fun onDownloadComplete() {
    view!!.dismissProgressDialog()
  }

  override fun subscribeDetail(context: Activity, model: MovieDetail) {
    movie = model.data!!.movie
    val youtubeUrl = if (movie!!.shot1 != null) {
      movie!!.shot1!!
    } else {
      Urls.getYouTubeImgUrl(movie!!.youtubeCode)
    }
    view!!.loadImages(youtubeUrl, movie!!.poster!!, movie!!.backgroundImage!!)
    torrents = movie!!.torrents
    setupQuality()
    setupInfo(movie!!.year, movie!!.genres, movie!!.rating, movie!!.description)
    if (Utils.isEmpty(movie!!.description)) view!!.hideSynopsis()
    if (movie!!.shot1 != null || movie!!.shot2 != null || movie!!.shot3 != null) {
      view!!.setPagerAdapter(ScreenshotsAdapter(movie!!.shot1!!, movie!!.shot2!!, movie!!.shot3!!))
    } else {
      view!!.hideScreenshots()
    }
    if (movie!!.cast != null && movie!!.cast!!.isNotEmpty()) {
      view!!.setCastRecyclerAdapter(CastAdapter(context, movie!!.cast!!))
    } else {
      view!!.hideCast()
    }
  }

  private fun setupQuality() {
    if (torrents != null && torrents!!.isNotEmpty()) {
      for (i in torrents!!.indices) {
        val torrent = torrents!![i]
        when {
          torrent.is3DAvailable -> view!!.enable3D()
          torrent.is720Available -> view!!.enable720p()
          torrent.is1080Available -> view!!.enable1080p()
        }
      }
    }
  }

  private fun setupInfo(year: String?, genres: List<String>?, rate: Float, description: String?) {
    if (!Utils.isEmpty(year)) {
      view!!.showYear()
    } else {
      view!!.hideYear()
    }
    if (genres != null && genres.isNotEmpty()) {
      view!!.showGenres()
    } else {
      view!!.hideGenres()
    }
    if (!Utils.isEmpty(rate.toString())) {
      view!!.showRate()
    } else {
      view!!.hideRate()
    }
    view!!.setInfo(year!!, this.getGenres(genres)!!, rate.toString(), description!!)
  }

  private fun getGenres(genres: List<String>?): String? {
    val builder = StringBuilder()
    if (genres != null && genres.isNotEmpty()) {
      for (i in genres.indices) {
        builder.append(genres[i])
        if (i != genres.size - 1) builder.append(", ")
      }
      return builder.toString()
    }
    return null
  }

  override fun subscribeDownload(isSaved: Boolean) {
    if (isSaved) {
      view!!.showFileSavedToast()
    } else {
      view!!.showFileNotSavedToast()
    }
  }

  override fun onDetailError() {
    view!!.hideProgressBar()
  }

  override fun onDownloadError(throwable: Throwable) {
    if (throwable !is NullPointerException) {
      view!!.dismissProgressDialog()
      view!!.showFileNotSavedToast()
    }
  }

  override fun writeFileToDisk(response: Response<ResponseBody>): Observable<Boolean> {
    return FileUtil.writeResponseToDisk(response)
  }

  override fun copyMagnet(context: Context, quality: Quality) {
    for (i in torrents!!.indices) {
      val torrent = torrents!![i]
      if (torrent.quality == quality.value) {
        Utils.copyTxt(context, Urls.buildMagnetUrl(
            torrent.hash, movie!!.titleLong, quality
        ))
        view!!.showCopyConfirmToast()
        break
      }
    }
  }

  override fun getMovieSize(quality: Quality): String? {
    for (i in torrents!!.indices) {
      val torrent = torrents!![i]
      if (torrent.quality == quality.value) {
        return torrent.size!!
      }
    }
    return null
  }
}
