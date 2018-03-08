package net.mEmoZz.yts.kotlin.ui.detail.adapters.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_cast.view.item_cast_iv_pic
import kotlinx.android.synthetic.main.item_cast.view.item_cast_tv_name
import net.mEmoZz.yts.kotlin.R
import net.mEmoZz.yts.kotlin.data.models.MovieDetail
import net.mEmoZz.yts.kotlin.utilities.GlideUtil
import net.mEmoZz.yts.kotlin.utilities.Utils

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class CastAdapter(
    private val context: Context, private val casts: List<MovieDetail.Cast>
) : RecyclerView.Adapter<CastAdapter.CastHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastHolder {
    val inflater = LayoutInflater.from(parent.context)
    val rowRoot = inflater.inflate(R.layout.item_cast, parent, false)
    return CastHolder(rowRoot)
  }

  override fun onBindViewHolder(holder: CastHolder, position: Int) {
    val cast = casts[position]
    if (cast.urlSmallImage != null && !Utils.isEmpty(cast.urlSmallImage)) {
      GlideUtil.loadImg(context, cast.urlSmallImage, holder.itemView.item_cast_iv_pic)
    }
    if (cast.name != null && !Utils.isEmpty(cast.name)) {
      holder.itemView.item_cast_tv_name.text = cast.name
    }
  }

  override fun getItemCount(): Int {
    return casts.size
  }

  inner class CastHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
}
