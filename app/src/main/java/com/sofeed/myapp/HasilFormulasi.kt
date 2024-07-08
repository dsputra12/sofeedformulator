package com.sofeed.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lpsolve.LinPro

class HasilFormulasi : AppCompatActivity() {

    // RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var mList: ArrayList<HasilData>
    private lateinit var adapter: HasilAdapter

    private lateinit var backButton: Button
    private lateinit var nextButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hasil_formulasi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize mList inside onCreate
        val hewan = intent.getDoubleArrayExtra("hewan")
        val data = intent.getParcelableArrayListExtra<ProsesData>("data")

        // Log the received data
        Log.d("HasilFormulasi", "Received hewan: ${hewan?.contentToString()}")
        Log.d("HasilFormulasi", "Received data: $data")

        // Check if hewan and data are not null before using them
        if (hewan != null && data != null) {
            mList = LinPro.calculate(hewan, data)
        } else {
            mList = ArrayList()
            Log.e("HasilFormulasi", "hewan or data is null")
        }

        if (mList.isNotEmpty()) {
            mList.forEach { i -> Toast.makeText(this, "Item: ${i.persen}", Toast.LENGTH_SHORT).show() }
        } else {
            Toast.makeText(this, "Item: Kosong", Toast.LENGTH_SHORT).show()
        }

        recyclerView = findViewById(R.id.recyclerViewHasil)
        adapter = HasilAdapter(this, mList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        backButton = findViewById(R.id.back_button)
        nextButton = findViewById(R.id.LihatKandunganNutrisi)

        backButton.setOnClickListener {
            finish()
        }
        nextButton.setOnClickListener {
            startActivity(Intent(this, LihatKandunganNutrisi::class.java))
        }
    }
}
