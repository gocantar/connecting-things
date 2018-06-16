package com.gocantar.connectingthings.domain.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by gocantar on 8/1/18.
 */
data class BulbStatus(private val switchedOn: Boolean = true, val color: Int, val alpha: Int, val effectID: Int, val period: Int = 0x00) : Parcelable {


    private constructor(parcel: Parcel) : this(
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (switchedOn) 1 else 0)
        parcel.writeInt(color)
        parcel.writeInt(alpha)
        parcel.writeInt(effectID)
        parcel.writeInt(period)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField val CREATOR = object: Parcelable.Creator<BulbStatus> {
            override fun createFromParcel(parcel: Parcel): BulbStatus {
                return BulbStatus(parcel)
            }

            override fun newArray(size: Int): Array<BulbStatus?> {
                return arrayOfNulls(size)
            }
        }
    }
}