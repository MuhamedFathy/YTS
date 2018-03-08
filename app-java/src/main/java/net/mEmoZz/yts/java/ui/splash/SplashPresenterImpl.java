package net.mEmoZz.yts.java.ui.splash;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class SplashPresenterImpl implements SplashPresenter {

  private SplashView splashView;

  SplashPresenterImpl(SplashView splashView) {
    this.splashView = splashView;
  }

  @Override public void onAttach() {
    splashView.openMainActivity();
  }

  @Override public void onDestroy() {
    splashView = null;
  }
}
