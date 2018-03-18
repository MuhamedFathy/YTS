package net.mEmoZz.yts.kotlin.data.bus

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

class MovieData(val movieId: Long, val movieName: String, val youtubeCode: String) : Parcelable {

  constructor(parcel: Parcel) : this(
      parcel.readLong(),
      parcel.readString(),
      parcel.readString())

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeLong(movieId)
    parcel.writeString(movieName)
    parcel.writeString(youtubeCode)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Creator<MovieData> {
    override fun createFromParcel(parcel: Parcel): MovieData {
      return MovieData(parcel)
    }

    override fun newArray(size: Int): Array<MovieData?> {
      return arrayOfNulls(size)
    }
  }
}
