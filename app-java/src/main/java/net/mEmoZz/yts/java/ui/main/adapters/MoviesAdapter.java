package net.mEmoZz.yts.java.ui.main.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.List;
import net.mEmoZz.yts.java.R;
import net.mEmoZz.yts.java.data.bus.MovieData;
import net.mEmoZz.yts.java.data.models.BaseMovie;
import net.mEmoZz.yts.java.ui.detail.DetailsActivity;
import net.mEmoZz.yts.java.utilities.GlideUtil;
import net.mEmoZz.yts.java.utilities.ToastUtil;
import net.mEmoZz.yts.java.utilities.Utils;
import org.greenrobot.eventbus.EventBus;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {

  private Activity context;
  private List<BaseMovie.Movie> moviesList;

  public MoviesAdapter(Activity context, List<BaseMovie.Movie> moviesList) {
    this.context = context;
    this.moviesList = moviesList;
  }

  @Override public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View rowRoot = inflater.inflate(R.layout.item_movies_list, parent, false);
    return new MoviesHolder(rowRoot);
  }

  @Override public void onBindViewHolder(MoviesHolder holder, int position) {
    fillViewWithData(holder, moviesList.get(position));
  }

  private void fillViewWithData(MoviesHolder holder, BaseMovie.Movie movie) {
    GlideUtil.loadItemImg(context, holder, movie.getPoster());
    holder.movieNameTextView.setText(movie.getTitle());
    holder.movieYearTextView.setText(movie.getYear());
    List<String> genres = movie.getGenres();
    if (genres != null && genres.size() > 0) {
      holder.movieGenreTextView.setText(movie.getGenres().get(0));
      holder.movieGenreTextView.setVisibility(View.VISIBLE);
    } else {
      holder.movieGenreTextView.setVisibility(View.GONE);
    }
  }

  @Override public int getItemCount() {
    return moviesList.size();
  }

  public class MoviesHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_card) public CardView rowCardView;
    @BindView(R.id.item_iv_poster) public ImageView moviePosterImageView;
    @BindView(R.id.item_relative_tintable) public RelativeLayout tintAbleView;
    @BindView(R.id.item_tv_movie_name) public TextView movieNameTextView;
    @BindView(R.id.item_tv_movie_year) public TextView movieYearTextView;
    @BindView(R.id.item_tv_movie_genre) public TextView movieGenreTextView;

    @BindString(R.string.no_network) String noConnection;

    MoviesHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick void onClick(View view) {
      if (Utils.isNetworkAvailable(view.getContext())) {
        BaseMovie.Movie movie = moviesList.get(getLayoutPosition());
        MovieData data = new MovieData(movie.getId(), movie.getTitle(), movie.getYoutubeCode());
        EventBus.getDefault().postSticky(data);
        Utils.startAnimatedActivity(context, DetailsActivity.class, view);
      } else {
        ToastUtil.showShortToast(view.getContext(), noConnection);
      }
    }
  }
}
