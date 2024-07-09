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
        }

        if (mList.isEmpty()) {
            Toast.makeText(this, "Infeasible", Toast.LENGTH_SHORT).show()
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
            if(mList.isEmpty()){
                Toast.makeText(this, "Infeasible", Toast.LENGTH_SHORT).show()
            }else{
                val hasilNutrisi = DoubleArray(11)
                for(i in mList)
                {
                    hasilNutrisi[0] += ((i.hasilProses.bahanKering * i.persen)/100)
                    hasilNutrisi[1] += ((i.hasilProses.abu * i.persen)/100)
                    hasilNutrisi[2] += ((i.hasilProses.pk*i.persen)/100)
                    hasilNutrisi[3] += ((i.hasilProses.lk*i.persen)/100)
                    hasilNutrisi[4] += ((i.hasilProses.sk*i.persen)/100)
                    hasilNutrisi[5] += ((i.hasilProses.betn*i.persen)/100)
                    hasilNutrisi[6] += ((i.hasilProses.tdn*i.persen)/100)
                    hasilNutrisi[7] += ((i.hasilProses.ca*i.persen)/100)
                    hasilNutrisi[8] += ((i.hasilProses.p*i.persen)/100)
                    hasilNutrisi[9] += ((i.hasilProses.metana*i.persen)/100)
                    hasilNutrisi[10] += ((i.hasilProses.harga*i.persen)/100)
                }
                val hasilIntent = Intent(this, LihatKandunganNutrisi::class.java)
                hasilIntent.putExtra("hasil",hasilNutrisi)
                startActivity(hasilIntent)
            }
        }
    }
}
