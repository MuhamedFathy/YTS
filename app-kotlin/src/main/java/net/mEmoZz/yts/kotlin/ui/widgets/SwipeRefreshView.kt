package net.mEmoZz.yts.kotlin.ui.widgets

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import net.mEmoZz.yts.kotlin.R

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class SwipeRefreshView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

  private var container: ViewGroup? = null

  init {
    setColorSchemeResources(R.color.colorAccent)
    isEnabled = false
  }

  override fun canChildScrollUp(): Boolean {
    val container = getContainer() ?: return false

    var view = container.getChildAt(0)
    if (view.visibility != View.VISIBLE) view = container.getChildAt(1)
    return view.canScrollVertically(-1)
  }

  private fun getContainer(): ViewGroup? {
    if (container != null) return container
    for (i in 0 until childCount) {
      if (getChildAt(i) is ViewGroup) {
        container = getChildAt(i) as ViewGroup
        break
      }
    }
    if (container == null) throw RuntimeException("Container view not found")
    return container
  }
}
