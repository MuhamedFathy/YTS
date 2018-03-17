package net.mEmoZz.yts.kotlin.ui.detail

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.jakewharton.rxbinding2.view.RxView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_details.collapsing_toolbar
import kotlinx.android.synthetic.main.activity_details.detail_frame_thumb
import kotlinx.android.synthetic.main.activity_details.detail_iv_toolbar_trailer_thumb
import kotlinx.android.synthetic.main.activity_details.toolbar
import kotlinx.android.synthetic.main.content_details.detail_card_view
import kotlinx.android.synthetic.main.content_details.detail_constraint
import kotlinx.android.synthetic.main.content_details.detail_divider1
import kotlinx.android.synthetic.main.content_details.detail_divider2
import kotlinx.android.synthetic.main.content_details.detail_iv_background
import kotlinx.android.synthetic.main.content_details.detail_iv_poster
import kotlinx.android.synthetic.main.content_details.detail_pager_screenshots
import kotlinx.android.synthetic.main.content_details.detail_progress_bar
import kotlinx.android.synthetic.main.content_details.detail_recycler_cast
import kotlinx.android.synthetic.main.content_details.detail_tv_cast
import kotlinx.android.synthetic.main.content_details.detail_tv_genre
import kotlinx.android.synthetic.main.content_details.detail_tv_overview
import kotlinx.android.synthetic.main.content_details.detail_tv_rate
import kotlinx.android.synthetic.main.content_details.detail_tv_res_1080
import kotlinx.android.synthetic.main.content_details.detail_tv_res_3d
import kotlinx.android.synthetic.main.content_details.detail_tv_res_720
import kotlinx.android.synthetic.main.content_details.detail_tv_screenshots
import kotlinx.android.synthetic.main.content_details.detail_tv_synopsis
import kotlinx.android.synthetic.main.content_details.detail_tv_year
import kotlinx.android.synthetic.main.dialog_download.view.dialog_download_iv_quality
import kotlinx.android.synthetic.main.dialog_download.view.dialog_download_tv_size
import net.mEmoZz.yts.kotlin.R
import net.mEmoZz.yts.kotlin.data.Quality
import net.mEmoZz.yts.kotlin.data.Quality.Q_1080P
import net.mEmoZz.yts.kotlin.data.Quality.Q_3D
import net.mEmoZz.yts.kotlin.data.Quality.Q_720P
import net.mEmoZz.yts.kotlin.data.bus.MovieData
import net.mEmoZz.yts.kotlin.data.network.Urls
import net.mEmoZz.yts.kotlin.ui.base.BaseActivity
import net.mEmoZz.yts.kotlin.ui.detail.DetailContract.Presenter
import net.mEmoZz.yts.kotlin.ui.detail.adapters.pager.ScreenshotsAdapter
import net.mEmoZz.yts.kotlin.ui.detail.adapters.recycler.CastAdapter
import net.mEmoZz.yts.kotlin.utilities.DialogUtil
import net.mEmoZz.yts.kotlin.utilities.GlideUtil
import net.mEmoZz.yts.kotlin.utilities.ToastUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class DetailsActivity : BaseActivity(), DetailContract.View {

  private val bus = EventBus.getDefault()

  private var presenter: Presenter? = null
  private var dialogUtil: DialogUtil? = null
  private var dialog: MaterialDialog? = null
  private var youtubeCode: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_details)
  }

  override fun initPresenter() {
    DetailPresenter().onAttach(this, DetailInteractorImpl())
  }


  override fun onStart() {
    super.onStart()
    if (!bus.isRegistered(this)) bus.register(this)
  }

  override fun onStop() {
    super.onStop()
    if (bus.isRegistered(this)) bus.unregister(this)
  }

  override fun onDestroy() {
    presenter!!.onDestroy()
    super.onDestroy()
  }

  @Subscribe(sticky = true, threadMode = ThreadMode.MAIN) fun receiveMovie(data: MovieData) {
    youtubeCode = data.youtubeCode
    title = data.movieName
    presenter!!.loadMovieDetails(context!!, data.movieId)
    bus.removeStickyEvent(data)
  }

  override fun setPresenter(presenter: DetailContract.Presenter) {
    this.presenter = presenter
  }

  override fun setActionBar() {
    setSupportActionBar(toolbar)
    val actionBar = supportActionBar
    actionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun setupRecycler() {
    detail_recycler_cast!!.layoutManager = LinearLayoutManager(context!!, HORIZONTAL, false)
  }

  override fun initDialog() {
    dialogUtil = DialogUtil(context!!)
  }

  override fun loadImages(tubeUrl: String, posterUrl: String, backgroundUrl: String) {
    GlideUtil.loadYouTubeThumb(context!!, collapsing_toolbar!!, detail_iv_toolbar_trailer_thumb!!,
        tubeUrl)
    GlideUtil.loadPoster(context!!, detail_card_view!!, detail_iv_poster!!, posterUrl)
    GlideUtil.loadImg(context!!, backgroundUrl, detail_iv_background!!)
  }

  override fun setInfo(year: String, genre: String, rate: String, description: String) {
    detail_tv_year!!.text = year
    detail_tv_genre!!.text = genre
    detail_tv_rate!!.text = getString(R.string.detail_vote, rate)
    detail_tv_overview!!.text = description
  }

  override fun setPagerAdapter(adapter: ScreenshotsAdapter) {
    detail_pager_screenshots!!.adapter = adapter
  }

  override fun setCastRecyclerAdapter(adapter: CastAdapter) {
    detail_recycler_cast!!.adapter = adapter
  }

  override fun showProgressBar() {
    detail_progress_bar!!.visibility = View.VISIBLE
  }

  override fun hideProgressBar() {
    detail_progress_bar!!.visibility = View.GONE
  }

  override fun showMainView() {
    detail_constraint!!.visibility = View.VISIBLE
  }

  override fun showYear() {
    detail_tv_year!!.visibility = View.VISIBLE
  }

  override fun hideYear() {
    detail_tv_year!!.visibility = View.GONE
  }

  override fun showGenres() {
    detail_tv_genre!!.visibility = View.VISIBLE
  }

  override fun hideGenres() {
    detail_tv_genre!!.visibility = View.GONE
  }

  override fun showRate() {
    detail_tv_rate!!.visibility = View.VISIBLE
  }

  override fun hideRate() {
    detail_tv_rate!!.visibility = View.GONE
  }

  override fun enable3D() {
    detail_tv_res_3d!!.isEnabled = true
    detail_tv_res_3d!!.setTextColor(Color.WHITE)
  }

  override fun enable720p() {
    detail_tv_res_720!!.isEnabled = true
    detail_tv_res_720!!.setTextColor(Color.WHITE)
  }

  override fun enable1080p() {
    detail_tv_res_1080!!.isEnabled = true
    detail_tv_res_1080!!.setTextColor(Color.WHITE)
  }

  override fun hideSynopsis() {
    detail_divider1!!.visibility = View.GONE
    detail_tv_overview!!.visibility = View.GONE
    detail_tv_synopsis!!.visibility = View.GONE
  }

  override fun hideScreenshots() {
    detail_divider1!!.visibility = View.GONE
    detail_tv_screenshots!!.visibility = View.GONE
    detail_pager_screenshots!!.visibility = View.GONE
  }

  override fun hideCast() {
    detail_divider2!!.visibility = View.GONE
    detail_tv_cast!!.visibility = View.GONE
    detail_recycler_cast!!.visibility = View.GONE
  }

  override fun showProgressDialog() {
    val builder = dialogUtil!!.buildProgressDialog(
        getString(R.string.dialog_title_downloading),
        getString(R.string.dialog_wait)
    )
    builder.cancelable(false)
    dialog = builder.build()
    dialog!!.show()
  }

  override fun dismissProgressDialog() {
    if (dialog != null) dialog!!.dismiss()
  }

  override fun showFileSavedToast() {
    ToastUtil.showLongToast(context!!, getString(R.string.msg_download_success))
  }

  override fun showFileNotSavedToast() {
    ToastUtil.showLongToast(context!!, getString(R.string.msg_download_failed))
  }

  override fun showCopyConfirmToast() {
    ToastUtil.showShortToast(context!!, getString(R.string.msg_magnet_copied))
  }

  override fun showNoConnectionToast() {
    ToastUtil.showShortToast(context!!, getString(R.string.no_network))
  }

  override fun showPermissionNotGrantedToast() {
    ToastUtil.showLongToast(context!!, getString(R.string.perm_not_granted))
  }

  override fun initClicks() {
    RxView.clicks(detail_frame_thumb).subscribe({
      val youTubeUri = Uri.parse(Urls.getYouTubeUrl(youtubeCode))
      startActivity(Intent(Intent.ACTION_VIEW, youTubeUri))
    }, {
      Timber.e(it)
    })
    RxView.clicks(detail_tv_res_3d).subscribe({
      showTorrentInfoDialog(Quality.Q_3D)
    }, {
      Timber.e(it)
    })
    RxView.clicks(detail_tv_res_720).subscribe({
      showTorrentInfoDialog(Quality.Q_720P)
    }, {
      Timber.e(it)
    })
    RxView.clicks(detail_tv_res_1080).subscribe({
      showTorrentInfoDialog(Quality.Q_1080P)
    }, {
      Timber.e(it)
    })
  }

  private fun showTorrentInfoDialog(quality: Quality) {
    val builder = dialogUtil!!.buildDialog(R.layout.dialog_download)
    builder.title(getString(R.string.dialog_download_torrent))
    setupInfoDialogButtons(builder, quality)
    val matDialog = builder.build()
    matDialog.setCancelable(true)
    val dialogView = matDialog.view

    when (quality) {
      Q_3D -> {
        dialogView.dialog_download_iv_quality.setImageDrawable(getDrawable(
            R.drawable.ic_3d_quality
        ))
        dialogView.dialog_download_tv_size.text = presenter!!.getMovieSize(Quality.Q_3D)
      }
      Q_720P -> {
        dialogView.dialog_download_iv_quality.setImageDrawable(getDrawable(
            R.drawable.ic_720p_quality
        ))
        dialogView.dialog_download_tv_size.text = presenter!!.getMovieSize(Quality.Q_720P)
      }
      Q_1080P -> {
        dialogView.dialog_download_iv_quality.setImageDrawable(getDrawable(
            R.drawable.ic_1080p_quality
        ))
        dialogView.dialog_download_tv_size.text = presenter!!.getMovieSize(Quality.Q_1080P)
      }
    }
    matDialog.show()
  }

  private fun setupInfoDialogButtons(builder: MaterialDialog.Builder, quality: Quality) {
    builder.positiveText(getString(R.string.dialog_download))
    builder.onPositive { _, _ -> requestPermission(quality) }

    builder.neutralText(getString(R.string.dialog_copy_magnet))
    builder.onNeutral { _, _ -> presenter!!.copyMagnet(builder.context, quality) }

    builder.negativeText(R.string.dialog_dismiss)
    builder.onNegative { dialog, _ -> dialog.dismiss() }
  }

  private fun requestPermission(quality: Quality) {
    val rxPermissions = RxPermissions(context!!)
    rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .subscribe({ granted ->
          presenter!!.onPermGranted(context!!, quality, granted!!)
        }, {
          Timber.e(it)
        })
  }
}
