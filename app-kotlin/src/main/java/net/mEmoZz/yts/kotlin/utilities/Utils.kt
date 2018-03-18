package net.mEmoZz.yts.kotlin.utilities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

object Utils {

  fun isNetworkAvailable(context: Context): Boolean {
    val mConnectivity = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager
    val mNetworkInfo = mConnectivity.activeNetworkInfo
    return mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting
  }

  fun isEmpty(text: String?): Boolean {
    return TextUtils.isEmpty(text)
  }

  fun copyTxt(context: Context, text: String?) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.primaryClip = ClipData.newPlainText("magnet", text)
  }
}