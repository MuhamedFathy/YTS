package net.mEmoZz.yts.java.ui.detail.adapters.pager;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.mEmoZz.yts.java.utilities.GlideUtil;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class ScreenshotsAdapter extends PagerAdapter {

  private List<String> images = new ArrayList<>();

  public ScreenshotsAdapter(String... images) {
    Collections.addAll(this.images, images);
  }

  @NonNull @Override public Object instantiateItem(@NonNull ViewGroup container, int position) {
    AppCompatImageView imageView = new AppCompatImageView(container.getContext());
    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT);
    imageView.setLayoutParams(params);
    imageView.setPadding(12, 0, 12, 0);
    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    container.addView(imageView);

    loadImage(imageView, position);
    return imageView;
  }

  private void loadImage(ImageView imageView, int position) {
    GlideUtil.loadImg(imageView.getContext(), images.get(position), imageView);
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((View) object);
  }

  @Override public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == object;
  }

  @Override public int getCount() {
    return images.size();
  }
}
