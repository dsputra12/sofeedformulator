package com.sofeed.myapp

import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.widget.CheckBox
import java.io.Serializable

data class FormulasiData(val namaPakan: String, val tipePakan: String, var hargaPakan: String, val gambarPakan: Int,
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
                         var isSelected: Boolean,
                         var rasio: Double) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
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
        parcel.readByte() != 0.toByte(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(namaPakan)
        parcel.writeString(tipePakan)
        parcel.writeString(hargaPakan)
        parcel.writeInt(gambarPakan)
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
        parcel.writeByte(if (isSelected) 1 else 0)
        parcel.writeDouble(rasio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FormulasiData> {
        override fun createFromParcel(parcel: Parcel): FormulasiData {
            return FormulasiData(parcel)
        }

        override fun newArray(size: Int): Array<FormulasiData?> {
            return arrayOfNulls(size)
        }
    }
}

