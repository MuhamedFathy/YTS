package net.mEmoZz.yts.kotlin.utilities

import android.os.Environment
import io.reactivex.Observable
import okhttp3.ResponseBody
import okio.Okio
import retrofit2.Response
import java.io.File
import java.io.IOException

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

object FileUtil {

  fun writeResponseToDisk(response: Response<ResponseBody>): Observable<File> {
    return Observable.create { emitter ->
      try {
        val header = response.headers().get("Content-Disposition")
        val fileName = header!!.replace("attachment; filename=", "")
            .replace("\"", "")
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(path, fileName)
        val sink = Okio.buffer(Okio.sink(file))
        sink.writeAll(response.body()!!.source())
        sink.close()
        emitter.onNext(file)
        emitter.onComplete()
      } catch (e: IOException) {
        e.printStackTrace()
        emitter.onError(e)
      }
    }
  }
}