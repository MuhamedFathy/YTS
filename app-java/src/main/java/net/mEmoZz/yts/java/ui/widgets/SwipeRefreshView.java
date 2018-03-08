package net.mEmoZz.yts.java.ui.widgets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import net.mEmoZz.yts.java.R;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class SwipeRefreshView extends SwipeRefreshLayout {

  private ViewGroup container;

  public SwipeRefreshView(Context context) {
    super(context);
  }

  public SwipeRefreshView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setColorSchemeResources(R.color.colorAccent);
    setEnabled(false);
  }

  @Override public boolean canChildScrollUp() {
    ViewGroup container = getContainer();
    if (container == null) return false;

    View view = container.getChildAt(0);
    if (view.getVisibility() != View.VISIBLE) view = container.getChildAt(1);
    return view.canScrollVertically(-1);
  }

  private ViewGroup getContainer() {
    if (container != null) return container;
    for (int i = 0; i < getChildCount(); i++) {
      if (getChildAt(i) instanceof ViewGroup) {
        container = (ViewGroup) getChildAt(i);
        break;
      }
    }
    if (container == null) throw new RuntimeException("Container view not found");
    return container;
  }
}
