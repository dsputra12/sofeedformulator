package com.sofeed.myapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.sofeed.myapp.databinding.FragmentHomePageBinding


class HomePage2 : Fragment() {
    private  lateinit var frame4 : FrameLayout
    private lateinit var frame1: FrameLayout
    private lateinit var  frame2: FrameLayout
    private lateinit var frame3: FrameLayout

    private val firebaseAuth = FirebaseAuth.getInstance()

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

        frame4 = view.findViewById(R.id.containerFormulasi)
        frame4.setOnClickListener{
            navigateToFormulasi()
        }

        frame1 = view.findViewById(R.id.containerInformasiPakan)
        frame1.setOnClickListener{
            navigateToInformasiPakan()
        }

        frame2 = view.findViewById(R.id.containerMetana)
        frame2.setOnClickListener{
            navigateToMetana()
        }

        frame3 = view.findViewById(R.id.containerMarket)
        frame3.setOnClickListener{
            navigateToMarket()
        }

        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser != null) {
            val username = firebaseUser.displayName
            val sambutan = "Hai, User!"
            val sambutan_baru = sambutan.replace("User", username ?: "User")
            binding.hai.text = sambutan_baru
        } else {
            startActivity(Intent(requireContext(), SignIn::class.java))
            activity?.finish()
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

    private fun navigateToInformasiPakan (){
        val InformasiPakanFragment = BahanPakan()
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, InformasiPakanFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToMetana(){
        val MetanaFragment = BahanPakan()
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, MetanaFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToMarket(){
        val MarketFragment = HargaPakan()
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, MarketFragment)
            .addToBackStack(null)
            .commit()
    }
}