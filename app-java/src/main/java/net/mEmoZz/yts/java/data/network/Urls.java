package net.mEmoZz.yts.java.data.network;

import net.mEmoZz.yts.java.data.Quality;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

public class Urls {

  public static final String BASE_URL = "https://yts.am/api/v2/";

  public static String getYouTubeImgUrl(String tubeId) {
    return "https://img.youtube.com/vi/" + tubeId + "/0.jpg";
  }

  public static String getYouTubeUrl(String tubeId) {
    return "https://www.youtube.com/watch?v=" + tubeId;
  }

  public static String buildMagnetUrl(String hash, String movieName, Quality quality) {
    return "magnet:?xt=urn:btih:"
        + hash
        + "&dn="
        + movieName.replaceAll(" ", "+")
        + "+[" + quality.value + "]+[YTS.AG]"
        + "&tr=udp://glotorrents.pw:6969/announce"
        + "&tr=udp://tracker.opentrackr.org:1337/announce"
        + "&tr=udp://torrent.gresille.org:80/announce"
        + "&tr=udp://tracker.openbittorrent.com:80"
        + "&tr=udp://tracker.coppersurfer.tk:6969"
        + "&tr=udp://tracker.leechers-paradise.org:6969"
        + "&tr= udp://p4p.arenabg.ch:1337"
        + "&tr=udp://tracker.internetwarriors.net:1337";
  }

  public static class ENDPOINTS {

    public static final String MOVIES_LIST = "list_movies.json";
    public static final String MOVIE_DETAILS = "movie_details.json";
  }
}
