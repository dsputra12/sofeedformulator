package com.sofeed.myapp

import android.text.Editable
import android.widget.CheckBox

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
                         var rasio: Double)

