package net.mEmoZz.yts.java;

import android.app.Application;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class YtsApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();

    initTimber();
    initCalligraphy();
  }

  private void initTimber() {
    if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
  }

  private void initCalligraphy() {
    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/Roboto-Regular.ttf")
        .setFontAttrId(R.attr.fontPath)
        .build());
  }
}
