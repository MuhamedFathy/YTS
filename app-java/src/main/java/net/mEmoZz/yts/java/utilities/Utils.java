package net.mEmoZz.yts.java.utilities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class Utils {

  public static boolean isNetworkAvailable(@NonNull Context context) {
    ConnectivityManager mConnectivity = (ConnectivityManager) context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    );
    NetworkInfo mNetworkInfo = mConnectivity.getActiveNetworkInfo();
    return mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting();
  }

  public static boolean isEmpty(String text) {
    return TextUtils.isEmpty(text);
  }

  public static void copyTxt(Context context, String text) {
    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(
        Context.CLIPBOARD_SERVICE
    );
    if (clipboard != null) clipboard.setPrimaryClip(ClipData.newPlainText("magnet", text));
  }
}
