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
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LakukanFormulasi : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var button: Button
    private lateinit var filterButton1: Button
    private lateinit var filterButton2: Button
    private lateinit var filterButton3: Button
    private var isFiltered1 = false
    private var isFiltered2 = false
    private var isFiltered3 = false

    @SuppressLint("MissingInflatedId")

    //untuk recycler view
    private var mList = ArrayList<FormulasiData>()
    private var hewan = DoubleArray(18)
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


        hewan = intent.getDoubleArrayExtra("hewan")!!
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
                if(i.isSelected) {
                    hasil.add(komponen)
                }
            }

            val intentLakukan = Intent(this,HasilFormulasi::class.java)
            intentLakukan.putExtra("hewan",hewan)
            intentLakukan.putParcelableArrayListExtra("data",hasil)

            startActivity(intentLakukan)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                query?.let {
                    searchFirestore(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let{
                    searchFirestore(it)
                }
                return false
            }
        })

        filterButton1 = findViewById(R.id.filterHijauan)
        filterButton1.setOnClickListener {
            if (!isFiltered1) {
                applyFilter("Hijauan")
                isFiltered1 = true
                filterButton1.backgroundTintList = ContextCompat.getColorStateList(this, R.color.hijau_muda2)

                // Deactivate other filters
                isFiltered2 = false
                filterButton2.backgroundTintList = ContextCompat.getColorStateList(this, R.color.not_selected)

                isFiltered3 = false
                filterButton3.backgroundTintList = ContextCompat.getColorStateList(this, R.color.not_selected)
            } else {
                showAllPakan()
                isFiltered1 = false
                filterButton1.backgroundTintList = ContextCompat.getColorStateList(this, R.color.not_selected)
            }
        }

        filterButton2 = findViewById(R.id.filterKonsentrat)
        filterButton2.setOnClickListener {
            if (!isFiltered2) {
                applyFilter("Konsentrat")
                isFiltered2 = true
                filterButton2.backgroundTintList = ContextCompat.getColorStateList(this, R.color.hijau_muda2)

                // Deactivate other filters
                isFiltered1 = false
                filterButton1.backgroundTintList = ContextCompat.getColorStateList(this, R.color.not_selected)

                isFiltered3 = false
                filterButton3.backgroundTintList = ContextCompat.getColorStateList(this, R.color.not_selected)
            } else {
                showAllPakan()
                isFiltered2 = false
                filterButton2.backgroundTintList = ContextCompat.getColorStateList(this, R.color.not_selected)
            }
        }

        filterButton3 = findViewById(R.id.filterMineral)
        filterButton3.setOnClickListener {
            if (!isFiltered3) {
                applyFilter("Mineral")
                isFiltered3 = true
                filterButton3.backgroundTintList = ContextCompat.getColorStateList(this, R.color.hijau_muda2)

                // Deactivate other filters
                isFiltered1 = false
                filterButton1.backgroundTintList = ContextCompat.getColorStateList(this, R.color.not_selected)

                isFiltered2 = false
                filterButton2.backgroundTintList = ContextCompat.getColorStateList(this, R.color.not_selected)
            } else {
                showAllPakan()
                isFiltered3 = false
                filterButton3.backgroundTintList = ContextCompat.getColorStateList(this, R.color.not_selected)
            }
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

    private fun searchFirestore(query: String){
        val db = Firebase.firestore
        db.collection("pakan")
            .orderBy("nama_pakan")
            .startAt(query)
            .endAt(query + "\uf8ff")
            .get()
            .addOnSuccessListener { documents ->
                val searchResults = ArrayList<FormulasiData>()
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

                    searchResults.add(
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
                updateRecyclerView(searchResults)
            }
            .addOnFailureListener { exception ->
                Log.e("LakukanFormulasi", "Error fetching data", exception)
                Toast.makeText(this, "Error fetching data: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun updateRecyclerView(searchResults: List<FormulasiData>) {
        mList.clear()
        mList.addAll(searchResults)
        saya.notifyDataSetChanged()
    }

    private fun applyFilter(tipePakan: String) {
        val filteredList = mList.filter { it.tipePakan == tipePakan } // Adjust as per your category condition
        updateFilteredRecyclerView(filteredList)
    }

    private fun showAllPakan() {
        mList.clear()
        addDataToList()
    }

    private fun updateFilteredRecyclerView(filteredResults: List<FormulasiData>) {
        mList.clear()
        mList.addAll(filteredResults)
        saya.notifyDataSetChanged()
    }
}