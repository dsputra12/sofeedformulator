package com.sofeed.myapp

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class BahanAdapter(context: Context, private var mList: ArrayList<FormulasiData>) :
    RecyclerView.Adapter<BahanAdapter.BahanViewHolder>() {

    inner class BahanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo : ImageView = itemView.findViewById(R.id.gambarBahan)
        val namaPakan : TextView = itemView.findViewById(R.id.namaBahan)
        val tipePakan : TextView = itemView.findViewById(R.id.tipeBahan)
        val hargaPakan : TextView = itemView.findViewById(R.id.nilaiBahan)
        val bk : TextView = itemView.findViewById(R.id.bahanKering)
        val abu : TextView = itemView.findViewById(R.id.abu)
        val pk : TextView = itemView.findViewById(R.id.pk)
        val lk : TextView = itemView.findViewById(R.id.lk)
        val sk : TextView = itemView.findViewById(R.id.sk)
        val tdn : TextView = itemView.findViewById(R.id.tdn)
        val ca : TextView = itemView.findViewById(R.id.ca)
        val p : TextView = itemView.findViewById(R.id.p)
        val metana : TextView = itemView.findViewById(R.id.metan)
        val betn : TextView = itemView.findViewById(R.id.betn)
        val constraintLayout : ConstraintLayout = itemView.findViewById(R.id.descLayout)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BahanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lihat_bahan, parent, false)
        return BahanViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: BahanViewHolder, position: Int) {
        holder.logo.setImageResource(mList[position].gambarPakan)
        holder.hargaPakan.text = String.format("Rp. %s", mList[position].hargaPakan)
        holder.namaPakan.text = mList[position].namaPakan
        holder.tipePakan.text = mList[position].tipePakan
        holder.bk.text = String.format("%.2f%%", mList[position].bahanKering)
        holder.abu.text = String.format("%.2f%%", mList[position].abu)
        holder.pk.text = String.format("%.2f%%", mList[position].pk)
        holder.lk.text = String.format("%.2f%%", mList[position].lk)
        holder.sk.text = String.format("%.2f%%", mList[position].sk)
        holder.betn.text = String.format("%.2f%%", mList[position].betn)
        holder.tdn.text = String.format("%.2f%%", mList[position].tdn)
        holder.ca.text = String.format("%.2f%%", mList[position].ca)
        holder.p.text = String.format("%.2f%%", mList[position].p)
        holder.metana.text = String.format("%.2f CO2eq", mList[position].metana)

        val isExpanded : Boolean = mList[position].isSelected
        holder.bk.visibility = if(isExpanded) View.VISIBLE else View.GONE
        holder.abu.visibility = if(isExpanded) View.VISIBLE else View.GONE
        holder.pk.visibility = if(isExpanded) View.VISIBLE else View.GONE
        holder.lk.visibility = if(isExpanded) View.VISIBLE else View.GONE
        holder.sk.visibility = if(isExpanded) View.VISIBLE else View.GONE
        holder.betn.visibility = if(isExpanded) View.VISIBLE else View.GONE
        holder.tdn.visibility = if(isExpanded) View.VISIBLE else View.GONE
        holder.ca.visibility = if(isExpanded) View.VISIBLE else View.GONE
        holder.p.visibility = if(isExpanded) View.VISIBLE else View.GONE
        holder.metana.visibility = if(isExpanded) View.VISIBLE else View.GONE

        holder.constraintLayout.setOnClickListener{
            mList[position].isSelected = !mList[position].isSelected
            notifyItemChanged(position)
        }
    }
}