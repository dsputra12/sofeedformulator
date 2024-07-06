package com.sofeed.myapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.sofeed.myapp.databinding.ActivityProfileSheetBinding
import com.sofeed.myapp.databinding.FragmentUsersProfileBinding

class UsersProfile : Fragment() {
    private lateinit var binding: FragmentUsersProfileBinding
    private lateinit var logout : Button
    private lateinit var editProfile : Button
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.saveChanges.setOnClickListener {
//            showBottomSheet()
//        }
        // Initialize Firebase Auth
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser != null) {
            binding.showUsername.text = firebaseUser.displayName
        } else {
            startActivity(Intent(requireContext(), SignIn::class.java))
            activity?.finish()
        }

        logout = view.findViewById(R.id.button_logout)
        logout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

        editProfile = view.findViewById(R.id.EditProfile)
        editProfile.setOnClickListener {
            navigateToEditProfile()
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

    // Example method or event handler
    private fun navigateToEditProfile() {
        // Navigate to EditProfileFragment using FragmentManager
        val editProfileFragment = EditProfile() // Replace with your actual fragment instantiation method
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, editProfileFragment)
            .addToBackStack(null) // Optional: Add to back stack for back navigation
            .commit()
    }
}
