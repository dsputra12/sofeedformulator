package com.sofeed.myapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.sofeed.myapp.databinding.FragmentBahanPakanBinding


class BahanPakan : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bahan_pakan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonDaftarBahanPakan: Button = view.findViewById(R.id.DaftarBahanPakan)
        buttonDaftarBahanPakan.setOnClickListener {
            startActivity(Intent(requireContext(), DaftarBahanPakan::class.java))
        }
    }

    private fun navigateToDaftarBahanPakan(){
        val PakanHijauanFragment = PakanHijauan() // Replace with your actual fragment instantiation method
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, PakanHijauanFragment)
            .addToBackStack(null) // Optional: Add to back stack for back navigation
            .commit()
    }
}