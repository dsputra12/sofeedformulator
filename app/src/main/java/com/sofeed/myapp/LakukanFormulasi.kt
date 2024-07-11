package com.sofeed.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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

    // For the recycler view
    private var originalList = ArrayList<FormulasiData>()
    private var filteredList = ArrayList<FormulasiData>()
    private lateinit var adapter: FormulasiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lakukan_formulasi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        button = findViewById(R.id.buttonFormulasi)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addDataToList()
        adapter = FormulasiAdapter(this, filteredList)
        recyclerView.adapter = adapter

        button.setOnClickListener {
            val hasil = ArrayList<ProsesData>()
            for (item in originalList) {
                if (item.isSelected) {
                    val komponen = ProsesData(
                        item.namaPakan,
                        item.minJumlah,
                        item.maxJumlah,
                        item.bahanKering,
                        item.abu,
                        item.pk,
                        item.lk,
                        item.sk,
                        item.betn,
                        item.tdn,
                        item.ca,
                        item.p,
                        item.metana,
                        item.hargaPakan.toDouble(),
                        item.rasio
                    )
                    hasil.add(komponen)
                }
            }

            val intentLakukan = Intent(this, HasilFormulasi::class.java)
            intentLakukan.putExtra("hewan", intent.getDoubleArrayExtra("hewan"))
            intentLakukan.putParcelableArrayListExtra("data", hasil)

            startActivity(intentLakukan)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchFirestore(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchFirestore(it) }
                return false
            }
        })

        filterButton1 = findViewById(R.id.filterHijauan)
        filterButton1.setOnClickListener {
            toggleFilter("Hijauan")
        }

        filterButton2 = findViewById(R.id.filterKonsentrat)
        filterButton2.setOnClickListener {
            toggleFilter("Konsentrat")
        }

        filterButton3 = findViewById(R.id.filterMineral)
        filterButton3.setOnClickListener {
            toggleFilter("Mineral")
        }
    }

    private fun addDataToList() {
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

                    val item = FormulasiData(
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
                    originalList.add(item)
                }
                filteredList.addAll(originalList)
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("LakukanFormulasi", "Error fetching data", exception)
                Toast.makeText(this, "Error fetching data: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun searchFirestore(query: String) {
        val searchResults = originalList.filter { it.namaPakan.contains(query, true) }
        updateRecyclerView(searchResults)
    }

    private fun updateRecyclerView(searchResults: List<FormulasiData>) {
        filteredList.clear()
        filteredList.addAll(searchResults)
        adapter.notifyDataSetChanged()
    }

    private fun applyFilter(tipePakan: String) {
        val filteredResults = originalList.filter { it.tipePakan == tipePakan }
        updateRecyclerView(filteredResults)
    }

    private fun showAllPakan() {
        filteredList.clear()
        filteredList.addAll(originalList)
        adapter.notifyDataSetChanged()
    }

    private fun updateFilteredRecyclerView(filteredResults: List<FormulasiData>) {
        filteredList.clear()
        filteredList.addAll(filteredResults)
        adapter.notifyDataSetChanged()
    }

    private fun toggleFilter(tipePakan: String) {
        when (tipePakan) {
            "Hijauan" -> {
                if (!isFiltered1) {
                    applyFilter("Hijauan")
                    isFiltered1 = true
                    filterButton1.backgroundTintList = ContextCompat.getColorStateList(this, R.color.hijau_muda2)
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
            "Konsentrat" -> {
                if (!isFiltered2) {
                    applyFilter("Konsentrat")
                    isFiltered2 = true
                    filterButton2.backgroundTintList = ContextCompat.getColorStateList(this, R.color.hijau_muda2)
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
            "Mineral" -> {
                if (!isFiltered3) {
                    applyFilter("Mineral")
                    isFiltered3 = true
                    filterButton3.backgroundTintList = ContextCompat.getColorStateList(this, R.color.hijau_muda2)
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
    }
}
