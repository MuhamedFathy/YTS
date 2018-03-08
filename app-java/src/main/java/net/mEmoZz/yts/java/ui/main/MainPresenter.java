package net.mEmoZz.yts.java.ui.main;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import net.mEmoZz.yts.java.ui.base.BasePresenter;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

interface MainPresenter extends BasePresenter {

  void loadMoviesList(Context context, int pageNum, boolean refresh);

  void activateEndlessScroll(RecyclerView recyclerView, GridLayoutManager manager);
}
