package net.mEmoZz.yts.kotlin.ui.splash

import net.mEmoZz.yts.kotlin.ui.splash.SplashContract.View

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class SplashPresenter : SplashContract.Presenter {

  private var view: SplashContract.View? = null

  override fun onAttach(view: View) {
    this.view = view
    this.view!!.setPresenter(this)
    this.view!!.openMainActivity()
  }

  override fun onDestroy() {
    view = null
  }
}
