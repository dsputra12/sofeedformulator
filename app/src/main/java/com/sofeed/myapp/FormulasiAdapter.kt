package com.sofeed.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FormulasiAdapter(var mList: List<FormulasiData>) :
    RecyclerView.Adapter<FormulasiAdapter.PakanViewHolder>() {

    inner class PakanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo : ImageView = itemView.findViewById(R.id.gambarPakan)
        val namaPakan : TextView = itemView.findViewById(R.id.nama_pakan)
        val tipePakan : TextView = itemView.findViewById(R.id.tipe_pakan)
        var hargaPakan : EditText = itemView.findViewById(R.id.nilai_harga)
        val checkBox : CheckBox = itemView.findViewById(R.id.checkbox)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PakanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bahan_pakan, parent, false)
        return PakanViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: PakanViewHolder, position: Int) {
        holder.logo.setImageResource(mList[position].gambarPakan)
        holder.namaPakan.text = mList[position].namaPakan

    }

}