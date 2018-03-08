package net.mEmoZz.yts.java.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import net.mEmoZz.yts.java.ui.main.MainActivity;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class SplashActivity extends AppCompatActivity implements SplashView {

  private SplashPresenter presenter;

  private void setupPresenter() {
    presenter = new SplashPresenterImpl(this);
    presenter.onAttach();
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setupPresenter();
  }

  @Override protected void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }

  @Override public void openMainActivity() {
    startActivity(new Intent(this, MainActivity.class));
    finish();
  }
}
