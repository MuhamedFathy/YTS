package net.mEmoZz.yts.kotlin

import android.app.Application
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class YtsApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    initTimber()
    initCalligraphy()
  }

  private fun initTimber() {
    if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
  }

  private fun initCalligraphy() {
    CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/Roboto-Regular.ttf")
        .setFontAttrId(R.attr.fontPath)
        .build())
  }
}