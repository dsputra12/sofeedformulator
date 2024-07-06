package com.sofeed.myapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sofeed.myapp.databinding.ActivityProfileSheetBinding
import com.sofeed.myapp.databinding.FragmentEditProfileBinding

class profile_sheet : AppCompatActivity() {
    private lateinit var binding: FragmentEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveChanges.setOnClickListener {
            showButtomSheet()
        }
    }


    private fun showButtomSheet(){
        val sheetDialog = BottomSheetDialog(this)
        val sheetBinding = ActivityProfileSheetBinding.inflate(layoutInflater)
        sheetDialog.apply {
            setContentView(sheetBinding.root)
            show()
        }

        sheetBinding.confirmButton.setOnClickListener {
            sheetDialog.dismiss()
        }
    }
}