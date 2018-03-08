package net.mEmoZz.yts.java.data.connection;

import java.util.concurrent.TimeUnit;
import net.mEmoZz.yts.java.BuildConfig;
import net.mEmoZz.yts.java.data.Urls;
import net.mEmoZz.yts.java.data.webservice.BaseApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class RetroConnect {

  public BaseApi initRetrofit() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.BASE_URL)
        .client(getClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    return retrofit.create(BaseApi.class);
  }

  private OkHttpClient getClient() {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(BuildConfig.DEBUG ? Level.BODY : Level.NONE);

    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .retryOnConnectionFailure(false);

    return clientBuilder.build();
  }
}
