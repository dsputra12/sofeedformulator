package com.sofeed.myapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.sofeed.myapp.databinding.ActivityHomepageBinding

class Homepage : AppCompatActivity() {
    private lateinit var logout : Button
    private lateinit var binding : ActivityHomepageBinding

    private var firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        logout = findViewById(R.id.logout)
        logout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }

      binding.bottomNavigationView.setOnNavigationItemSelectedListener(){ item: MenuItem ->
            when (item.itemId) {
                R.id.homepage -> replaceFragment(HomePage2())
                R.id.hargaPakan -> replaceFragment(HargaPakan())
                R.id.bahanPakan -> replaceFragment(BahanPakan())
                R.id.formulasi ->{
                    replaceFragment(Formulasi())
                    Toast.makeText(this, "Ini berhasil", Toast.LENGTH_SHORT).show()
                }
                R.id.userProfile -> replaceFragment(UsersProfile())
                else -> {}
            }
            true
        }


        // Initially select the first fragment
        if (savedInstanceState == null) {
            replaceFragment(HomePage2())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}