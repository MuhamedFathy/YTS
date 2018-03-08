package net.mEmoZz.yts.kotlin.utilities

import android.app.Activity
import android.support.annotation.LayoutRes
import com.afollestad.materialdialogs.MaterialDialog

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class DialogUtil(private val context: Activity) {

  companion object {
    private const val FONT_MEDUIM = "Roboto-Medium.ttf"
    private const val FONT_REGULAR = "Roboto-Regular.ttf"
  }

  fun buildDialog(@LayoutRes id: Int): MaterialDialog.Builder {
    return MaterialDialog.Builder(context)
        .customView(id, false)
        .typeface(FONT_MEDUIM, FONT_REGULAR)
  }

  fun buildProgressDialog(title: String, msg: String): MaterialDialog.Builder {
    return MaterialDialog.Builder(context)
        .title(title)
        .content(msg)
        .typeface(FONT_MEDUIM, FONT_REGULAR)
        .progress(true, 0)
  }
}
