package com.sofeed.myapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.sofeed.myapp.databinding.FragmentBahanPakanBinding


class BahanPakan : Fragment() {
    private lateinit var bahanPakanHijauan : Button
    private lateinit var bahanPakanKonsentrat: Button
    private lateinit var MineralDanVitamin: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bahan_pakan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bahanPakanHijauan = view.findViewById(R.id.bahanPakanHijauan)
        bahanPakanKonsentrat = view.findViewById(R.id.bahanPakanKonsentrat)
        MineralDanVitamin = view.findViewById(R.id.MineralDanVitamin)

        bahanPakanHijauan.setOnClickListener {
            navigateToBahanPakanHijauan()
        }

//        bahanPakanKonsentrat.setOnClickListener {
//            navigateToBahanPakanKonsentrat()
//        }
//
//        MineralDanVitamin.setOnClickListener {
//            navigateToMineralDanVitamin()
//        }
    }

    private fun navigateToBahanPakanHijauan(){
        val PakanHijauanFragment = PakanHijauan() // Replace with your actual fragment instantiation method
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, PakanHijauanFragment)
            .addToBackStack(null) // Optional: Add to back stack for back navigation
            .commit()
    }
}