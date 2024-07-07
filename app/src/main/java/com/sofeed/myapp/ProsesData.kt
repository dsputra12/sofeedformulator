package com.sofeed.myapp

import android.os.Parcel
import android.os.Parcelable

data class ProsesData(val nama : String,
                      val minJumlah :Double,
                      val maxJumlah :Double,
                      val bahanKering: Double,
                      val abu: Double,
                      val pk: Double,
                      val lk: Double,
                      val sk: Double,
                      val betn: Double,
                      val tdn: Double,
                      val ca: Double,
                      val p: Double,
                      val metana: Double,
                      val harga: Double,
                      val rasio : Double) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nama)
        parcel.writeDouble(minJumlah)
        parcel.writeDouble(maxJumlah)
        parcel.writeDouble(bahanKering)
        parcel.writeDouble(abu)
        parcel.writeDouble(pk)
        parcel.writeDouble(lk)
        parcel.writeDouble(sk)
        parcel.writeDouble(betn)
        parcel.writeDouble(tdn)
        parcel.writeDouble(ca)
        parcel.writeDouble(p)
        parcel.writeDouble(metana)
        parcel.writeDouble(harga)
        parcel.writeDouble(rasio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProsesData> {
        override fun createFromParcel(parcel: Parcel): ProsesData {
            return ProsesData(parcel)
        }

        override fun newArray(size: Int): Array<ProsesData?> {
            return arrayOfNulls(size)
        }
    }
}