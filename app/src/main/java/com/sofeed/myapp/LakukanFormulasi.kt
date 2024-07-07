package com.sofeed.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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

class LakukanFormulasi : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var button: Button
    @SuppressLint("MissingInflatedId")
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

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            Toast.makeText(this, "Item: $itemSelected", Toast.LENGTH_SHORT).show()
        }

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        button = findViewById(R.id.buttonFormulasi)

        button.setOnClickListener {
            startActivity(Intent(this,HasilFormulasi::class.java))
        }


        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addDataToList()
    }
    private fun addDataToList(){

    }
}