package net.mEmoZz.yts.java.ui.base;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public interface BasePresenter<V extends BaseView, I extends BaseInteractor> {

  void onAttach(V view, I interactor);

  void onDestroy();
}
