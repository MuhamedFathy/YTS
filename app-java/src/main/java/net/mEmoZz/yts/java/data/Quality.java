package net.mEmoZz.yts.java.data;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public enum Quality {

  Q_3D("3D"),
  Q_720P("720p"),
  Q_1080P("1080p");

  public String value;

  Quality(String value) {
    this.value = value;
  }
}
