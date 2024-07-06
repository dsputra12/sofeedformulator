package com.sofeed.myapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sofeed.myapp.databinding.FragmentHomePageBinding


class HomePage2 : Fragment() {
    private  lateinit var imageView : ImageView
    private lateinit var binding: FragmentHomePageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Apply window insets
        enableEdgeToEdge(binding.root)

        imageView = view.findViewById(R.id.cardFormulasi)
        imageView.setOnClickListener{
            navigateToFormulasi()
        }
    }

    private fun navigateToFormulasi (){
        val formulasiFragment = Formulasi()
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, formulasiFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun enableEdgeToEdge(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }
}