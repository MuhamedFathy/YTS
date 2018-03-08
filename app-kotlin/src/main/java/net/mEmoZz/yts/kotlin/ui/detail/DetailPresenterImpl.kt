package net.mEmoZz.yts.kotlin.ui.detail

import android.Manifest
import android.app.Activity
import android.content.Context
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import net.mEmoZz.yts.kotlin.data.Quality
import net.mEmoZz.yts.kotlin.data.Urls
import net.mEmoZz.yts.kotlin.data.connection.RetroConnect
import net.mEmoZz.yts.kotlin.data.models.MovieDetail
import net.mEmoZz.yts.kotlin.ui.detail.adapters.pager.ScreenshotsAdapter
import net.mEmoZz.yts.kotlin.ui.detail.adapters.recycler.CastAdapter
import net.mEmoZz.yts.kotlin.utilities.FileUtil
import net.mEmoZz.yts.kotlin.utilities.Utils
import timber.log.Timber

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class DetailPresenterImpl internal constructor(
    private var detailView: DetailView?
) : DetailPresenter {

  private val disposables = CompositeDisposable()
  private val api = RetroConnect().initRetrofit()

  private var movie: MovieDetail.Movie? = null
  private var torrents: List<MovieDetail.Torrent>? = null

  override fun onAttach() {
    detailView!!.setActionBar()
    detailView!!.setupRecycler()
    detailView!!.initDialog()
    detailView!!.initClicks()
  }

  override fun onDestroy() {
    disposables.dispose()
    detailView = null
  }

  override fun loadMovieDetails(context: Activity, movieId: Long) {
    if (Utils.isNetworkAvailable(context)) {
      api.getMovieDetails(movieId, true, true)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .doOnSubscribe({
            disposables.add(it)
            detailView!!.showProgressBar()
          }).doOnComplete({
            detailView!!.hideProgressBar()
            detailView!!.showMainView()
          }).subscribe({
            movie = it.data!!.movie
            val youtubeUrl = Urls.getYouTubeImgUrl(movie!!.youtubeCode)
            detailView!!.loadImages(youtubeUrl, movie!!.poster, movie!!.backgroundImage)
            torrents = movie!!.torrents
            setupQuality()
            setupInfo(movie!!.year, movie!!.genres, movie!!.rating.toString(), movie!!.description)
            if (Utils.isEmpty(movie!!.description)) detailView!!.hideSynopsis()
            if (isScreenshotsAvailable()) {
              detailView!!.setPagerAdapter(ScreenshotsAdapter(
                  movie!!.screenshotImage1!!,
                  movie!!.screenshotImage2!!,
                  movie!!.screenshotImage3!!
              ))
            } else {
              detailView!!.hideScreenshots()
            }
            if (movie!!.cast != null && movie!!.cast!!.isNotEmpty()) {
              detailView!!.setCastRecyclerAdapter(CastAdapter(context, movie!!.cast!!))
            } else {
              detailView!!.hideCast()
            }
          }, {
            Timber.e(it)
          })
    }
  }

  private fun isScreenshotsAvailable(): Boolean {
    return movie!!.screenshotImage1 != null
           || movie!!.screenshotImage2 != null
           || movie!!.screenshotImage3 != null
  }

  override fun downloadFile(context: Activity, quality: Quality) {
    val rxPermissions = RxPermissions(context)
    rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .doOnSubscribe { disposables.add(it) }
        .subscribe {
          if (it!!) {
            for (i in torrents!!.indices) {
              val torrent = torrents!![i]
              if (torrent.quality.equals(quality.value)) {
                if (Utils.isNetworkAvailable(context)) {
                  api.downloadFile(torrent.url!!)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe({
                        disposables.add(it)
                        detailView!!.showProgressDialog()
                      }).doOnComplete({ detailView!!.dismissProgressDialog() })
                      .flatMap { FileUtil.writeResponseToDisk(it) }
                      .subscribe({ detailView!!.showFileSavedToast() }, {
                        detailView!!.dismissProgressDialog()
                        detailView!!.showFileNotSavedToast()
                        Timber.e(it)
                      })
                } else {
                  detailView!!.showNoConnectionToast()
                }
                break
              }
            }
          } else {
            detailView!!.showPermissionNotGrantedToast()
          }
        }
  }

  override fun copyMagnet(context: Context, quality: Quality) {
    for (i in torrents!!.indices) {
      val torrent = torrents!![i]
      if (torrent.quality.equals(quality.value)) {
        Utils.copyTxt(context, Urls.buildMagnetUrl(
            torrent.hash, movie!!.titleLong, quality
        ))
        detailView!!.showCopyConfirmToast()
        break
      }
    }
  }

  override fun getMovieSize(quality: Quality): String? {
    for (i in torrents!!.indices) {
      val torrent = torrents!![i]
      if (torrent.quality.equals(quality.value)) {
        return torrent.size
      }
    }
    return null
  }

  private fun setupQuality() {
    if (torrents != null && torrents!!.isNotEmpty()) {
      for (i in torrents!!.indices) {
        val torrent = torrents!![i]
        when {
          torrent.is3DAvailable -> detailView!!.enable3D()
          torrent.is720Available -> detailView!!.enable720p()
          torrent.is1080Available -> detailView!!.enable1080p()
        }
      }
    }
  }

  private fun setupInfo(year: String?, genres: List<String>?, rate: String?, description: String?) {
    if (!Utils.isEmpty(year)) {
      detailView!!.showYear()
    } else {
      detailView!!.hideYear()
    }
    if (genres != null && genres.isNotEmpty()) {
      detailView!!.showGenres()
    } else {
      detailView!!.hideGenres()
    }
    if (!Utils.isEmpty(rate)) {
      detailView!!.showRate()
    } else {
      detailView!!.hideRate()
    }
    detailView!!.setInfo(year, getGenres(genres), rate, description)
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
}
