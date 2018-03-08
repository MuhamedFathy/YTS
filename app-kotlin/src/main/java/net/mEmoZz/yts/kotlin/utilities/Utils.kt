package net.mEmoZz.yts.kotlin.utilities

import android.app.Activity
import android.app.ActivityOptions
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.text.TextUtils
import android.view.View

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

  fun startAnimatedActivity(context: Activity, cls: Class<*>, shared: View) {
    val options = ActivityOptions.makeSceneTransitionAnimation(context, shared, "")
    val intent = Intent(context, cls)
    context.startActivity(intent, options.toBundle())
  }

  fun isEmpty(text: String?): Boolean {
    return TextUtils.isEmpty(text)
  }

  fun copyTxt(context: Context, text: String?) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.primaryClip = ClipData.newPlainText("magnet", text)
  }
}