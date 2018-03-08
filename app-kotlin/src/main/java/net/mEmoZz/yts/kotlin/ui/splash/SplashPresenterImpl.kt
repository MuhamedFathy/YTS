package net.mEmoZz.yts.kotlin.ui.splash

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

internal class SplashPresenterImpl(private var splashView: SplashView?) : SplashPresenter {

  override fun onAttach() {
    splashView!!.openMainActivity()
  }

  override fun onDestroy() {
    splashView = null
  }
}
