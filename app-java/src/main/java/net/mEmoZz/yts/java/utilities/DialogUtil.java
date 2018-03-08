package net.mEmoZz.yts.java.utilities;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class DialogUtil {

  private Activity context;

  private static final String FONT_MEDUIM = "Roboto-Medium.ttf";
  private static final String FONT_REGULAR = "Roboto-Regular.ttf";

  public DialogUtil(Activity context) {
    this.context = context;
  }

  public MaterialDialog.Builder buildDialog(@LayoutRes int id) {
    return new MaterialDialog.Builder(context)
        .customView(id, false)
        .typeface(FONT_MEDUIM, FONT_REGULAR);
  }

  public MaterialDialog.Builder buildProgressDialog(String title, String msg) {
    return new MaterialDialog.Builder(context)
        .title(title)
        .content(msg)
        .typeface(FONT_MEDUIM, FONT_REGULAR)
        .progress(true, 0);
  }
}
