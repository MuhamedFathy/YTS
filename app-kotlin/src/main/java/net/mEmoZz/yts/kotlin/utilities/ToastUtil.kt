package net.mEmoZz.yts.kotlin.utilities

import android.content.Context
import android.widget.Toast

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

object ToastUtil {

  fun showShortToast(context: Context, msg: String?) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
  }

  fun showLongToast(context: Context, msg: String?) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }
}
