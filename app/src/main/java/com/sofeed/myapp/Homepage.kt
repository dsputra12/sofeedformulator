package com.sofeed.myapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
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
    private lateinit var binding : ActivityHomepageBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }
//        logout = findViewById(R.id.logout)
//        logout.setOnClickListener {
//            firebaseAuth.signOut()
//            startActivity(Intent(this, MainActivity::class.java))
//        }
        bottomNavigationView = binding.bottomNavigationView
        frameLayout = binding.frameLayout

      binding.bottomNavigationView.setOnItemSelectedListener(){ item: MenuItem ->
            when (item.itemId) {
                R.id.homepage -> replaceFragment(HomePage2())
                R.id.hargaPakan -> replaceFragment(HargaPakan())
                R.id.bahanPakan -> replaceFragment(BahanPakan())
                R.id.formulasi ->{
                    replaceFragment(Formulasi())
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

    fun hideBottomNavigation() {
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
    }

    fun showBottomNavigation() {
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
    }
}