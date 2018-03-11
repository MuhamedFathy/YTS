package net.mEmoZz.yts.kotlin.ui.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.mEmoZz.yts.kotlin.ui.main.MainActivity
import net.mEmoZz.yts.kotlin.ui.splash.SplashContract.View

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class SplashActivity : AppCompatActivity(), View {

  private var presenter: SplashPresenter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    SplashPresenter().onAttach(this)
  }

  override fun onDestroy() {
    presenter!!.onDestroy()
    super.onDestroy()
  }

  override fun setPresenter(presenter: SplashPresenter) {
    this.presenter = presenter
  }

  override fun openMainActivity() {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
  }
}
