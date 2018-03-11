package net.mEmoZz.yts.java.utilities;

import android.os.Environment;
import io.reactivex.Observable;
import java.io.File;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

@SuppressWarnings("ConstantConditions") public class FileUtil {

  public static Observable<Boolean> writeResponseToDisk(Response<ResponseBody> response) {
    return Observable.create(emitter -> {
      try {
        String header = response.headers().get("Content-Disposition");
        String fileName = header.replace("attachment; filename=", "")
            .replace("\"", "");
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, fileName);
        BufferedSink sink = Okio.buffer(Okio.sink(file));
        sink.writeAll(response.body().source());
        sink.close();
        emitter.onNext(file.exists());
        emitter.onComplete();
      } catch (Exception e) {
        emitter.onError(e);
      }
    });
  }
}
