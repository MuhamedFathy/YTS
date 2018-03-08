package net.mEmoZz.yts.kotlin.ui.detail.adapters.pager

import android.support.v4.view.PagerAdapter
import android.support.v7.widget.AppCompatImageView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import net.mEmoZz.yts.kotlin.utilities.GlideUtil
import java.util.ArrayList
import java.util.Collections

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class ScreenshotsAdapter(vararg images: String) : PagerAdapter() {

  private val images = ArrayList<String>()

  init {
    Collections.addAll(this.images, *images)
  }

  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    val imageView = AppCompatImageView(container.context)
    val params = ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT)
    imageView.layoutParams = params
    imageView.setPadding(12, 0, 12, 0)
    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
    container.addView(imageView)

    loadImage(imageView, position)
    return imageView
  }

  private fun loadImage(imageView: ImageView, position: Int) {
    GlideUtil.loadImg(imageView.context, images[position], imageView)
  }

  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    container.removeView(`object` as View)
  }

  override fun isViewFromObject(view: View, `object`: Any): Boolean {
    return view === `object`
  }

  override fun getCount(): Int {
    return images.size
  }
}
