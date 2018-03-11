package net.mEmoZz.yts.java.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import net.mEmoZz.yts.java.ui.main.MainActivity;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

  private SplashContract.Presenter presenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new SplashPresenter().onAttach(this, null);
  }

  @Override protected void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }

  @Override public void setPresenter(SplashContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override public void openMainActivity() {
    startActivity(new Intent(this, MainActivity.class));
    finish();
  }
}
