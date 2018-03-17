package net.mEmoZz.yts.java.ui.main.adapters.callback;

import android.support.v7.util.DiffUtil;
import java.util.List;
import net.mEmoZz.yts.java.data.network.models.BaseMovie;

/**
 * Authored by Mohamed Fathy on 17 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class MoviesDiffCallback extends DiffUtil.Callback {

  private List<BaseMovie.Movie> oldMovieList;
  private List<BaseMovie.Movie> newMovieList;

  public MoviesDiffCallback(List<BaseMovie.Movie> oldMovieList,
      List<BaseMovie.Movie> newMovieList) {
    this.oldMovieList = oldMovieList;
    this.newMovieList = newMovieList;
  }

  @Override public int getOldListSize() {
    return oldMovieList != null ? oldMovieList.size() : 0;
  }

  @Override public int getNewListSize() {
    return newMovieList != null ? newMovieList.size() : 0;
  }

  @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return oldMovieList.get(oldItemPosition).getId() == newMovieList.get(newItemPosition).getId();
  }

  @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    return oldMovieList.get(oldItemPosition).equals(newMovieList.get(newItemPosition));
  }
}
