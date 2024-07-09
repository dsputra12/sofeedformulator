package com.sofeed.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        signInButton = findViewById(R.id.signInButton)
        signUpButton = findViewById(R.id.signOutButton)

        signInButton.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }
}