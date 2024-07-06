//package com.sofeed.myapp
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//
//import androidx.recyclerview.widget.RecyclerView
//
//class PakanHijauanAdapter(var mlist: List<PakanHijauan>):RecyclerView.Adapter<PakanHijauanAdapter.PakanHijauanViewHolder>(){
//
//    inner class PakanHijauanViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        val gambar : ImageView = itemView.findViewById(R.id.gambarPakanHijauan)
//        val nama : TextView = itemView.findViewById(R.id.namaPakanHijauan)
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PakanHijauanViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bahan_pakan, parent, false)
//        return PakanHijauanViewHolder(view)
//    }
//
////    override fun onBindViewHolder(holder: PakanHijauanViewHolder, position: Int) {
////        val currentItem = mlist[position]
////        holder.gambar.setImageResource(currentItem.gambarPakan)
////        holder.nama.text = currentItem.namaPakanHijauan
////    }
//
//    override fun getItemCount(): Int {
//        return mlist.size
//    }
//}