package net.mEmoZz.yts.java.utilities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

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

  public static void startAnimatedActivity(Activity context, Class cls, View shared) {
    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context, shared, "");
    Intent intent = new Intent(context, cls);
    context.startActivity(intent, options.toBundle());
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
