package net.mEmoZz.yts.java.ui.splash;

import net.mEmoZz.yts.java.ui.base.BaseInteractor;
import net.mEmoZz.yts.java.ui.base.BasePresenter;
import net.mEmoZz.yts.java.ui.base.BaseView;

/**
 * Authored by Mohamed Fathy on 10 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public interface SplashContract {

  interface View extends BaseView<Presenter> {

    void openMainActivity();

    @Override default void setActionBar() {
    }

    @Override default void showProgressBar() {
    }

    @Override default void hideProgressBar() {
    }
  }

  interface Presenter extends BasePresenter<View, BaseInteractor> {
  }
}
