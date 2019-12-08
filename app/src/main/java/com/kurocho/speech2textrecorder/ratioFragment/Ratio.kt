package com.kurocho.speech2textrecorder.ratioFragment

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Ratio(// "spelling": "tekst", "ratio": 4.079987506661232
    val spelling: String, val ratio: Double?) : Serializable, Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {}

    companion object CREATOR : Parcelable.Creator<Ratio> {
        override fun createFromParcel(parcel: Parcel): Ratio {
            return Ratio(parcel)
        }

        override fun newArray(size: Int): Array<Ratio?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(parcel.readString(),
        parcel.readDouble())
}
