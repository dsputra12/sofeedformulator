package com.sofeed.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import lpsolve.LinPro

class HasilFormulasi : AppCompatActivity() {

    private lateinit var backButton: Button
    private lateinit var nextButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = intent
        val hewan = intent.getDoubleArrayExtra("hewan")
        val data = intent.getParcelableArrayListExtra<ProsesData>("data")

        val result = LinPro.calculate(hewan,data)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hasil_formulasi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        backButton = findViewById(R.id.back_button)
        nextButton = findViewById(R.id.LihatKandunganNutrisi)



        backButton.setOnClickListener {
            finish()
        }
        nextButton.setOnClickListener {
            startActivity(Intent(this,LihatKandunganNutrisi::class.java))
        }
    }
}