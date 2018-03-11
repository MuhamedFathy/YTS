package net.mEmoZz.yts.kotlin.ui.base

import net.mEmoZz.yts.kotlin.data.connection.RetroConnect

/**
 * Authored by Mohamed Fathy on 11 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

open class BaseInteractorImpl {

  protected var api = RetroConnect().initRetrofit()
}
