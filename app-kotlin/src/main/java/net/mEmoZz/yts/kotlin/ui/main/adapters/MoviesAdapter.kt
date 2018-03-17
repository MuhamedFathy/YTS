package net.mEmoZz.yts.kotlin.ui.main.adapters

import android.app.Activity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.item_movies_list.view.item_card
import kotlinx.android.synthetic.main.item_movies_list.view.item_tv_movie_genre
import kotlinx.android.synthetic.main.item_movies_list.view.item_tv_movie_name
import kotlinx.android.synthetic.main.item_movies_list.view.item_tv_movie_year
import net.mEmoZz.yts.kotlin.R
import net.mEmoZz.yts.kotlin.data.bus.MovieData
import net.mEmoZz.yts.kotlin.data.network.models.BaseMovie
import net.mEmoZz.yts.kotlin.ui.detail.DetailsActivity
import net.mEmoZz.yts.kotlin.ui.main.adapters.callback.MoviesDiffCallback
import net.mEmoZz.yts.kotlin.utilities.GlideUtil
import net.mEmoZz.yts.kotlin.utilities.Utils
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class MoviesAdapter(
    private val context: Activity,
    private val movieList: MutableList<BaseMovie.Movie>
) : RecyclerView.Adapter<MoviesAdapter.MoviesHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
    val inflater = LayoutInflater.from(parent.context)
    val rowRoot = inflater.inflate(R.layout.item_movies_list, parent, false)
    return MoviesHolder(rowRoot)
  }

  override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
    fillViewWithData(holder, movieList[position])
  }

  private fun fillViewWithData(holder: MoviesHolder, movie: BaseMovie.Movie) {
    GlideUtil.loadItemImg(context, holder, movie.poster!!)
    holder.itemView.item_tv_movie_name!!.text = movie.title
    holder.itemView.item_tv_movie_year!!.text = movie.year
    val genres = movie.genres
    if (genres != null && genres.isNotEmpty()) {
      holder.itemView.item_tv_movie_genre!!.text = movie.genres[0]
      holder.itemView.item_tv_movie_genre!!.visibility = View.VISIBLE
    } else {
      holder.itemView.item_tv_movie_genre!!.visibility = View.GONE
    }
  }

  fun updateData(movieList: List<BaseMovie.Movie>, newList: Boolean) {
    if (newList) {
      val result = DiffUtil.calculateDiff(
          MoviesDiffCallback(this.movieList, movieList)
      )
      this.movieList.clear()
      result.dispatchUpdatesTo(this)
    }
    this.movieList.addAll(movieList)
    if (!newList) notifyItemRangeInserted(itemCount, this.movieList.size - 1)
  }

  override fun getItemCount(): Int {
    return movieList.size
  }

  inner class MoviesHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

    private val noConnection = context.getString(R.string.no_network)

    init {
      RxView.clicks(view.item_card).subscribe({ onClick(view) }, { Timber.e(it) })
    }

    private fun onClick(view: View) {
      if (Utils.isNetworkAvailable(view.context)) {
        val movie = movieList[layoutPosition]
        val data = MovieData(movie.id, movie.title!!, movie.youtubeCode!!)
        EventBus.getDefault().postSticky(data)
        Utils.startAnimatedActivity(context, DetailsActivity::class.java, view)
      } else {
        Toast.makeText(view.context, noConnection, Toast.LENGTH_SHORT).show()
      }
    }
  }
}
