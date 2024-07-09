package com.sofeed.myapp

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.time.times

class HasilAdapter(context: Context, private var mList: ArrayList<HasilData>) :
    RecyclerView.Adapter<HasilAdapter.HasilViewHolder>() {


    inner class HasilViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaHasil : TextView = itemView.findViewById(R.id.nama_hasil)
        var hargaHasil : TextView = itemView.findViewById(R.id.harga_hasil)
        val persen : TextView = itemView.findViewById(R.id.persen_hasil)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HasilViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hasil_formulasi, parent, false)
        return HasilViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: HasilViewHolder, position: Int) {
        holder.namaHasil.text = mList[position].hasilProses.nama
        val haruga = (mList[position].hasilProses.harga * mList[position].persen) / 100
        holder.hargaHasil.text = String.format("Rp%.2f",haruga)
        holder.persen.text = String.format("%.2f%%",mList[position].persen)
    }

}