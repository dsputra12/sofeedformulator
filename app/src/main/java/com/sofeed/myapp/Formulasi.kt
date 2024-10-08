package com.sofeed.myapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sofeed.myapp.databinding.FragmentFormulasiBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.Normalizer.Form


class Formulasi : Fragment() {
    private lateinit var binding: FragmentFormulasiBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFormulasiBinding.inflate(inflater,container, false)
        return (binding.root)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val container1: FrameLayout = view.findViewById(R.id.LakukanFormulasi)
        val container2: FrameLayout = view.findViewById(R.id.RekapFormulasi)

        enableEdgeToEdge(binding.root)

        container1.setOnClickListener {
            val intent = Intent(requireContext(), PilihHewan::class.java)
            startActivity(intent)
        }

        container2.setOnClickListener {
            showBelumTersedia(view)
        }
    }

    private fun enableEdgeToEdge(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun showBelumTersedia(view: View){
        Toast.makeText(view.context, "Fitur ini masih dalam tahap pengembangan!", Toast.LENGTH_SHORT).show()
    }
}