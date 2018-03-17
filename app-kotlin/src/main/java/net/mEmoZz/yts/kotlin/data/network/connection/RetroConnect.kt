package net.mEmoZz.yts.kotlin.data.network.connection

import net.mEmoZz.yts.kotlin.BuildConfig
import net.mEmoZz.yts.kotlin.data.network.Urls
import net.mEmoZz.yts.kotlin.data.network.webservice.BaseApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */
class RetroConnect {

  fun initRetrofit(): BaseApi {
    val retrofit = Retrofit.Builder().baseUrl(Urls.BASE_URL)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(BaseApi::class.java)
  }

  private val client: OkHttpClient
    get() {
      val loggingInterceptor = HttpLoggingInterceptor()
      loggingInterceptor.level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE

      val clientBuilder = OkHttpClient.Builder()
          .connectTimeout(30, TimeUnit.SECONDS)
          .readTimeout(30, TimeUnit.SECONDS)
          .writeTimeout(30, TimeUnit.SECONDS)
          .addInterceptor(loggingInterceptor)
          .retryOnConnectionFailure(false)

      return clientBuilder.build()
    }
}
