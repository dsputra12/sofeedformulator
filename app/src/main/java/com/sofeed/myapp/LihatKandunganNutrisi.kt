package com.sofeed.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LihatKandunganNutrisi : AppCompatActivity() {
    private lateinit var homeButton : Button
    private lateinit var bahanKering : TextView
    private lateinit var abu : TextView
    private lateinit var pk : TextView
    private lateinit var lk : TextView
    private lateinit var sk : TextView
    private lateinit var tdn : TextView
    private lateinit var ca : TextView
    private lateinit var p : TextView
    private lateinit var metana : TextView
    private lateinit var harga : TextView
    private lateinit var saveButton: Button
    private lateinit var shareButton: Button

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lihat_kandungan_nutrisi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        val mList = intent.getDoubleArrayExtra("hasil")!!

        bahanKering = findViewById(R.id.nilaiBahanKering)
        abu = findViewById(R.id.nilaiAbu)
        pk = findViewById(R.id.nilaiProtein)
        lk = findViewById(R.id.nilaiLemak)
        sk = findViewById(R.id.nilaiSerat)
        tdn = findViewById(R.id.nilaiTDN)
        ca = findViewById(R.id.nilaiCa)
        p = findViewById(R.id.nilaiP)
        metana = findViewById(R.id.nilaiMetana)
        harga = findViewById(R.id.nilaiHarga)

        bahanKering.text = String.format("%.3f%%", mList[0])
        abu.text = String.format("%.3f%%", mList[1])
        pk.text = String.format("%.3f%%", mList[2])
        lk.text = String.format("%.3f%%", mList[3])
        sk.text = String.format("%.3f%%", mList[4])
        tdn.text = String.format("%.3f%%", mList[6])
        ca.text = String.format("%.3f%%", mList[7])
        p.text = String.format("%.3f%%", mList[8])
        metana.text = String.format("%.3f", mList[9])
        harga.text = String.format("Rp%.2f,-", mList[10])

        homeButton = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            navigateToHomepage()
        }

        saveButton = findViewById(R.id.saveButton)
        saveButton.setOnClickListener { view ->
            showBelumTersedia(view)
        }

        shareButton = findViewById(R.id.shareButton)
        shareButton.setOnClickListener {view ->
            showBelumTersedia(view)
        }

        val metanaValue = mList[9]
        if (metanaValue <= 15.000){
            metana.setTextColor(ContextCompat.getColor(this, R.color.hijau_baik))
        }
        else if(metanaValue > 15.000 && metanaValue <= 23.000){
            metana.setTextColor(ContextCompat.getColor(this, R.color.kuning_tua))
        }
        else if(metanaValue > 23.000 && metanaValue <= 30.000){
            metana.setTextColor(ContextCompat.getColor(this, R.color.merah2))
        }
    }



    private fun navigateToHomepage(){
        val intent = Intent(this, Homepage::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun showBelumTersedia(view: View){
        Toast.makeText(view.context, "Fitur ini masih dalam tahap pengembangan!", Toast.LENGTH_SHORT).show()
    }
}