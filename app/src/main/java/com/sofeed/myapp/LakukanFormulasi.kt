package com.sofeed.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LakukanFormulasi : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var button: Button
    @SuppressLint("MissingInflatedId")

    //untuk recycler view
    private var mList = ArrayList<FormulasiData>()
    private lateinit var saya: FormulasiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_lakukan_formulasi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        val items = listOf("Sapi Pedaging", "Sapi Perah")

        val autoComplete : AutoCompleteTextView = findViewById(R.id.autoComplete)

        val adapter = ArrayAdapter(this, R.layout.jenis_hewan, items)
        autoComplete.setAdapter(adapter)

        val hewan = DoubleArray(18)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            if (itemSelected == "Sapi Pedaging"){
                hewan[0] = 86.0
                hewan[1] = 0.0
                hewan[2] = 13.0
                hewan[3] = 0.0
                hewan[4] = 0.0
                hewan[5] = 0.0
                hewan[6] = 68.0
                hewan[7] = 0.6
                hewan[8] = 0.4
                hewan[9] = 100.0
                hewan[10] = 100.0
                hewan[11] = 100.0
                hewan[12] = 7.0
                hewan[13] = 100.0
                hewan[14] = 100.0
                hewan[15] = 100.0
                hewan[16] = 1.2
                hewan[17] = 0.8
            }else{
                hewan[0] = 86.0
                hewan[1] = 0.0
                hewan[2] = 18.0
                hewan[3] = 0.0
                hewan[4] = 0.0
                hewan[5] = 0.0
                hewan[6] = 68.0
                hewan[7] = 0.8
                hewan[8] = 0.4
                hewan[9] = 100.0
                hewan[10] = 100.0
                hewan[11] = 100.0
                hewan[12] = 7.0
                hewan[13] = 100.0
                hewan[14] = 100.0
                hewan[15] = 100.0
                hewan[16] = 1.3
                hewan[17] = 0.8
            }
            Toast.makeText(this, "Item: $itemSelected", Toast.LENGTH_SHORT).show()
        }

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        button = findViewById(R.id.buttonFormulasi)




        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        if(mList.isEmpty())
        {
            addDataToList()
        }

        saya = FormulasiAdapter(this, mList)
        recyclerView.adapter = saya

        button.setOnClickListener {
            /*for(i in mList){
                val nama = i.namaPakan
                val status = i.isSelected
                val harga = i.hargaPakan
                Toast.makeText(this, "Item: $nama $harga $status", Toast.LENGTH_SHORT).show()
            }*/
            val hasil = ArrayList<ProsesData>()
            for(i in mList){
                val komponen = ProsesData(
                    i.namaPakan,
                    i.minJumlah,
                    i.maxJumlah,
                    i.bahanKering,
                    i.abu,
                    i.pk,
                    i.lk,
                    i.sk,
                    i.betn,
                    i.tdn,
                    i.ca,
                    i.p,
                    i.metana,
                    i.hargaPakan.toDouble(),
                    i.rasio
                )
                hasil.add(komponen)
            }

            val intentLakukan = Intent(this,HasilFormulasi::class.java)
            intentLakukan.putExtra("hewan",hewan)
            intentLakukan.putParcelableArrayListExtra("data",hasil)

            startActivity(intentLakukan)
        }
    }
    private fun addDataToList(){
        val db = Firebase.firestore

        db.collection("pakan").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val kategori = document.getString("kategori").orEmpty()
                    val nama = document.getString("nama_pakan").orEmpty()
                    val bahanKering = document.getDouble("bahan_kering") ?: 0.0
                    val abu = document.getDouble("abu") ?: 0.0
                    val minJumlah = document.getDouble("min_jumlah") ?: 0.0
                    val maxJumlah = document.getDouble("max_jumlah") ?: 0.0
                    val pk = document.getDouble("pk") ?: 0.0
                    val lk = document.getDouble("lk") ?: 0.0
                    val sk = document.getDouble("sk") ?: 0.0
                    val betn = document.getDouble("betn") ?: 0.0
                    val tdn = document.getDouble("tdn") ?: 0.0
                    val ca = document.getDouble("ca") ?: 0.0
                    val p = document.getDouble("p") ?: 0.0
                    val metana = document.getDouble("metana") ?: 0.0
                    val ratio = document.getDouble("ratio") ?: 0.0
                    val harga = document.getDouble("harga").toString()

                    val gambar: Int = when (kategori) {
                        "Hijauan" -> R.drawable.pakan_hijauan
                        "Konsentrat" -> R.drawable.pakan_konsentrat
                        else -> R.drawable.pakan_mineral
                    }

                    Log.d("LakukanFormulasi", "Fetched item: $nama")

                    mList.add(
                        FormulasiData(
                            nama,
                            kategori,
                            harga,
                            gambar,
                            minJumlah,
                            maxJumlah,
                            bahanKering,
                            abu,
                            pk,
                            lk,
                            sk,
                            betn,
                            tdn,
                            ca,
                            p,
                            metana,
                            false,
                            ratio
                        )
                    )
                }
                Log.d("LakukanFormulasi", "Total items fetched: ${mList.size}")
                saya.notifyDataSetChanged() // Notify adapter of data changes
            }
            .addOnFailureListener { exception ->
                Log.e("LakukanFormulasi", "Error fetching data", exception)
                Toast.makeText(this, "Error fetching data: ${exception.message}", Toast.LENGTH_LONG).show()
            }

        /*mList.add(FormulasiData("Rumput Ijo", "Hijauan", "1000", R.drawable.pakan_hijauan,
            0.0, 70.0, 17.9, 13.8, 9.7, 2.0, 36.1, 38.4,
            58.0, 0.36, 0.29, 28.0, false, 0.34))
        mList.add(FormulasiData("Rumput Biru", "Hijauan", "1000", R.drawable.pakan_hijauan,
            0.0, 70.0, 17.9, 13.8, 9.7, 2.0, 36.1, 38.4,
            58.0, 0.36, 0.29, 28.0, false, 0.34))
        mList.add(FormulasiData("Rumput Ungu", "Hijauan", "1000", R.drawable.pakan_hijauan,
            0.0, 70.0, 17.9, 13.8, 9.7, 2.0, 36.1, 38.4,
            58.0, 0.36, 0.29, 28.0, false, 0.34))*/
    }
}