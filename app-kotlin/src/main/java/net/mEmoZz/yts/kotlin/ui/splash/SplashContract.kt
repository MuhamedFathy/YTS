package net.mEmoZz.yts.kotlin.ui.splash

/**
 * Authored by Mohamed Fathy on 10 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

interface SplashContract {

  interface View {

    fun setPresenter(presenter: SplashPresenter)

    fun openMainActivity()
  }

  interface Presenter {

    fun onAttach(view: SplashContract.View)

    fun onDestroy()
  }
}
