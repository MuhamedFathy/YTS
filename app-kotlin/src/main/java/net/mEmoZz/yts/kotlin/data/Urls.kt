package net.mEmoZz.yts.kotlin.data

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

object Urls {

  const val BASE_URL = "https://yts.am/api/v2/"

  fun getYouTubeImgUrl(tubeId: String?): String {
    return "https://img.youtube.com/vi/$tubeId/0.jpg"
  }

  fun getYouTubeUrl(tubeId: String?): String {
    return "https://www.youtube.com/watch?v=$tubeId"
  }

  fun buildMagnetUrl(hash: String?, movieName: String?, quality: Quality?): String? {
    return ("magnet:?xt=urn:btih:"
            + hash
            + "&dn="
            + movieName!!.replace(" ".toRegex(), "+")
            + "+[" + quality!!.value + "]+[YTS.AG]"
            + "&tr=udp://glotorrents.pw:6969/announce"
            + "&tr=udp://tracker.opentrackr.org:1337/announce"
            + "&tr=udp://torrent.gresille.org:80/announce"
            + "&tr=udp://tracker.openbittorrent.com:80"
            + "&tr=udp://tracker.coppersurfer.tk:6969"
            + "&tr=udp://tracker.leechers-paradise.org:6969"
            + "&tr= udp://p4p.arenabg.ch:1337"
            + "&tr=udp://tracker.internetwarriors.net:1337")
  }

  object ENDPOINTS {

    const val MOVIES_LIST = "list_movies.json"
    const val MOVIE_DETAILS = "movie_details.json"
  }
}
