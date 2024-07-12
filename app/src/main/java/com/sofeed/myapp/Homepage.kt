package com.sofeed.myapp

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
    private lateinit var gestureDetector: GestureDetector


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

        gestureDetector = GestureDetector(this, SwipeGestureListener())

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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
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

    inner class SwipeGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            try {
                val diffY = e2?.y?.minus(e1?.y ?: 0f) ?: 0f
                val diffX = e2?.x?.minus(e1?.x ?: 0f) ?: 0f
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }
    }

    private fun onSwipeLeft() {
        // Handle right swipe, transition to previous fragment
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
        when (currentFragment) {
            is HomePage2 -> replaceFragment(HargaPakan())
            is HargaPakan -> replaceFragment(BahanPakan())
            is BahanPakan -> replaceFragment(Formulasi())
            is Formulasi -> replaceFragment(UsersProfile())
        }
    }

    private fun onSwipeRight() {
        // Handle left swipe, transition to next fragment
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
        when (currentFragment) {
            is HargaPakan -> replaceFragment(HomePage2())
            is BahanPakan -> replaceFragment(HargaPakan())
            is Formulasi -> replaceFragment(BahanPakan())
            is UsersProfile -> replaceFragment(Formulasi())
        }
    }
}