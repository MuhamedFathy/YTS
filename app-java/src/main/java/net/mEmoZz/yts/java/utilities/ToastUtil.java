package net.mEmoZz.yts.java.utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class ToastUtil {

  public static void showShortToast(Context context, String msg) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
  }

  public static void showLongToast(Context context, String msg) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
  }
}
