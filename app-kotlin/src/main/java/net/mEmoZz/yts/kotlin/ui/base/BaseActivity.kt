package net.mEmoZz.yts.kotlin.ui.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

abstract class BaseActivity : AppCompatActivity() {

  protected var context: Activity? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    context = this
  }

  override fun setContentView(layoutResID: Int) {
    super.setContentView(layoutResID)
    initPresenter()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> super.onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun attachBaseContext(newBase: Context) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
  }

  protected abstract fun initPresenter()
}
