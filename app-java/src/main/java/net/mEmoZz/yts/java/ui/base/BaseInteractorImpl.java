package net.mEmoZz.yts.java.ui.base;

import net.mEmoZz.yts.java.data.connection.RetroConnect;
import net.mEmoZz.yts.java.data.webservice.BaseApi;

/**
 * Authored by Mohamed Fathy on 11 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class BaseInteractorImpl {

  protected BaseApi api = new RetroConnect().initRetrofit();
}
