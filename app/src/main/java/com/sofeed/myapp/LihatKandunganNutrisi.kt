package com.sofeed.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
    }



    private fun navigateToHomepage(){
        val intent = Intent(this, Homepage::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}