package com.sofeed.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class UserProfile : AppCompatActivity() {
    private lateinit var textUsername : TextView

    private val firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textUsername = findViewById(R.id.showUsername)
        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser != null){
            textUsername.text = firebaseUser.displayName
        }
        else{
            startActivity(Intent(this, SignIn::class.java ))
            finish()
        }
    }
}