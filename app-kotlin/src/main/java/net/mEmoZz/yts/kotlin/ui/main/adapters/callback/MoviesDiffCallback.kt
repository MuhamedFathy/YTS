package net.mEmoZz.yts.kotlin.ui.main.adapters.callback

import android.support.v7.util.DiffUtil
import net.mEmoZz.yts.kotlin.data.network.models.BaseMovie.Movie

/**
 * Authored by Mohamed Fathy on 17 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class MoviesDiffCallback(
    private val oldMovieList: List<Movie>?,
    private val newMovieList: List<Movie>?
) : DiffUtil.Callback() {

  override fun getOldListSize(): Int {
    return oldMovieList?.size ?: 0
  }

  override fun getNewListSize(): Int {
    return newMovieList?.size ?: 0
  }

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldMovieList!![oldItemPosition].id == newMovieList!![newItemPosition].id
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldMovieList!![oldItemPosition] == newMovieList!![newItemPosition]
  }
}
