package net.mEmoZz.yts.kotlin.ui.base

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

interface BasePresenter<in V, in I : BaseInteractor> {

  fun onAttach(view: V, interactor: I)

  fun onDestroy()
}