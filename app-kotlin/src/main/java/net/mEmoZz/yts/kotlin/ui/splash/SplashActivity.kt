package net.mEmoZz.yts.kotlin.ui.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.mEmoZz.yts.kotlin.ui.main.MainActivity

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class SplashActivity : AppCompatActivity(), SplashView {

  private var presenter: SplashPresenter? = null

  private fun setupPresenter() {
    presenter = SplashPresenterImpl(this)
    presenter!!.onAttach()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setupPresenter()
  }

  override fun onDestroy() {
    presenter!!.onDestroy()
    super.onDestroy()
  }

  override fun openMainActivity() {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
  }
}
