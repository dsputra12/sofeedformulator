package com.sofeed.myapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sofeed.myapp.databinding.ActivityProfileSheetBinding
import com.sofeed.myapp.databinding.FragmentEditProfileBinding


class EditProfile : Fragment() {
    private  lateinit var binding: FragmentEditProfileBinding
    private lateinit var BackButton: ImageButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveChanges.setOnClickListener {
            showBottomSheet()
        }

        // Hide the bottom navigation bar
        (activity as? Homepage)?.hideBottomNavigation()

        BackButton = view.findViewById(R.id.back_button)
        BackButton.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun showBottomSheet() {
        val sheetDialog = BottomSheetDialog(requireContext())
        val sheetBinding = ActivityProfileSheetBinding.inflate(layoutInflater)
        sheetDialog.apply {
            setContentView(sheetBinding.root)
            show()
        }

        sheetBinding.confirmButton.setOnClickListener {
            sheetDialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Show the bottom navigation bar
        (activity as? Homepage)?.showBottomNavigation()
    }
}