package net.mEmoZz.yts.kotlin.utilities

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.CardView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.github.florent37.glidepalette.BitmapPalette.Profile
import com.github.florent37.glidepalette.BitmapPalette.Swatch
import com.github.florent37.glidepalette.GlidePalette
import kotlinx.android.synthetic.main.item_movies_list.view.item_card
import kotlinx.android.synthetic.main.item_movies_list.view.item_iv_poster
import kotlinx.android.synthetic.main.item_movies_list.view.item_relative_tintable
import kotlinx.android.synthetic.main.item_movies_list.view.item_tv_movie_genre
import kotlinx.android.synthetic.main.item_movies_list.view.item_tv_movie_name
import kotlinx.android.synthetic.main.item_movies_list.view.item_tv_movie_year
import net.mEmoZz.yts.kotlin.ui.main.adapters.MoviesAdapter
import net.mEmoZz.yts.kotlin.utilities.GlideUtil.Type.ITEM
import net.mEmoZz.yts.kotlin.utilities.GlideUtil.Type.POSTER
import net.mEmoZz.yts.kotlin.utilities.GlideUtil.Type.YOUTUBE

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

object GlideUtil {

  private enum class Type {
    ITEM, YOUTUBE, POSTER
  }

  fun loadImg(context: Context, url: String, target: ImageView) {
    Glide.with(context).load(Uri.parse(url)).into(target)
  }

  fun loadItemImg(context: Context, holder: MoviesAdapter.MoviesHolder, url: String) {
    Glide.with(context)
        .load(url)
        .listener(getPalette(ITEM, holder, null, null, url))
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(RequestOptions.centerCropTransform())
        .into(holder.itemView.item_iv_poster)
  }

  fun loadYouTubeThumb(
      context: Context, toolbar: CollapsingToolbarLayout,
      imageView: ImageView, url: String
  ) {
    Glide.with(context)
        .load(url)
        .listener(getPalette(YOUTUBE, null, toolbar, null, url))
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(RequestOptions.centerCropTransform())
        .into(imageView)
  }

  fun loadPoster(context: Context, card: CardView, imageView: ImageView, url: String) {
    Glide.with(context)
        .load(url)
        .listener(getPalette(POSTER, null, null, card, url))
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(RequestOptions.centerCropTransform())
        .into(imageView)
  }

  private fun getPalette(
      type: Type, holder: MoviesAdapter.MoviesHolder?,
      toolbar: CollapsingToolbarLayout?, card: CardView?, url: String
  ): RequestListener<Drawable>? {
    val glidePalette = GlidePalette.with(url).use(Profile.VIBRANT)
    if (holder != null) {
      glidePalette.intoBackground(holder.itemView.item_relative_tintable, Swatch.RGB)
          .intoTextColor(holder.itemView.item_tv_movie_name, Swatch.BODY_TEXT_COLOR)
          .intoTextColor(holder.itemView.item_tv_movie_year, Swatch.BODY_TEXT_COLOR)
          .intoTextColor(holder.itemView.item_tv_movie_genre, Swatch.BODY_TEXT_COLOR)
    }
    glidePalette.crossfade(true)
        .intoCallBack { palette ->
          if (palette != null) {
            val vibrantSwatch = palette.vibrantSwatch
            val mutedSwatch = palette.mutedSwatch
            if (vibrantSwatch != null) {
              when (type) {
                ITEM -> if (holder != null) {
                  holder.itemView.item_card.setCardBackgroundColor(vibrantSwatch.rgb)
                  if (vibrantSwatch.rgb == Color.TRANSPARENT && mutedSwatch != null) {
                    holder.itemView.item_card.setCardBackgroundColor(mutedSwatch.rgb)
                  }
                }
                YOUTUBE -> {
                  toolbar!!.setBackgroundColor(vibrantSwatch.rgb)
                  toolbar.setContentScrimColor(vibrantSwatch.rgb)
                  if (vibrantSwatch.rgb == Color.TRANSPARENT && mutedSwatch != null) {
                    toolbar.setBackgroundColor(mutedSwatch.rgb)
                    toolbar.setContentScrimColor(mutedSwatch.rgb)
                  }
                }
                POSTER -> {
                  card!!.setCardBackgroundColor(vibrantSwatch.rgb)
                  if (vibrantSwatch.rgb == Color.TRANSPARENT && mutedSwatch != null) {
                    card.setCardBackgroundColor(mutedSwatch.rgb)
                  }
                }
              }
            }
          }
        }
    return glidePalette
  }
}
