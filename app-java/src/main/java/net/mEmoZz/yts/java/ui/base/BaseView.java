package net.mEmoZz.yts.java.ui.base;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public interface BaseView<T extends BasePresenter> {

  void setPresenter(T presenter);

  void setActionBar();

  void showProgressBar();

  void hideProgressBar();
}
