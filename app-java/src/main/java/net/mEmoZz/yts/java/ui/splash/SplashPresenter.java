package net.mEmoZz.yts.java.ui.splash;

import net.mEmoZz.yts.java.ui.base.BaseInteractor;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class SplashPresenter implements SplashContract.Presenter {

  private SplashContract.View view;

  @Override public void onAttach(SplashContract.View view, BaseInteractor interactor) {
    this.view = view;
    this.view.setPresenter(this);
    this.view.openMainActivity();
  }

  @Override public void onDestroy() {
    view = null;
  }
}
