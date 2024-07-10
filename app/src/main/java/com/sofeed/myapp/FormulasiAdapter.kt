package com.sofeed.myapp

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

class FormulasiAdapter(context: Context,private var mList: ArrayList<FormulasiData>) :
    RecyclerView.Adapter<FormulasiAdapter.PakanViewHolder>() {
        

    inner class PakanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo : ImageView = itemView.findViewById(R.id.gambarPakan)
        val namaPakan : TextView = itemView.findViewById(R.id.nama_pakan)
        val tipePakan : TextView = itemView.findViewById(R.id.tipe_pakan)
        var hargaPakan : EditText = itemView.findViewById(R.id.nilai_harga)
        val checkBoxtv : CheckBox = itemView.findViewById(R.id.checkbox)
        init{
            checkBoxtv.setOnClickListener { v ->
                val isChecked = (v as CheckBox).isChecked
                mList[adapterPosition].isSelected = isChecked

                notifyDataSetChanged()

                for (i in mList.indices){
                    Log.d("TAG", mList.toString() )
                }
            }
        }
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
        holder.tipePakan.text = mList[position].tipePakan
        holder.checkBoxtv.isChecked = mList[position].isSelected

        holder.hargaPakan.removeTextChangedListener(holder.hargaPakan.tag as? TextWatcher)

        holder.hargaPakan.setText(mList[position].hargaPakan)

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val currentPos = holder.adapterPosition
                if (currentPos != RecyclerView.NO_POSITION) {
                    mList[currentPos].hargaPakan = s.toString()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No need to call setText here
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No need to call setText here
            }
        }

        holder.hargaPakan.addTextChangedListener(textWatcher)
        holder.hargaPakan.tag = textWatcher
    }

}

