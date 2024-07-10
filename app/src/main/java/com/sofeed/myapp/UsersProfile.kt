package com.sofeed.myapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.sofeed.myapp.databinding.ActivityProfileSheetBinding
import com.sofeed.myapp.databinding.FragmentUsersProfileBinding

class UsersProfile : Fragment() {
    private lateinit var binding: FragmentUsersProfileBinding
    private lateinit var logout : TextView
    private lateinit var editProfile : TextView
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var feedbackButton : TextView
    private lateinit var settingsButton: TextView
    private lateinit var changePassword: TextView

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

        editProfile = view.findViewById(R.id.editProfile)
        editProfile.setOnClickListener {
            navigateToEditProfile()
        }

        feedbackButton = view.findViewById(R.id.feedback)
        feedbackButton.setOnClickListener {
            val url = "https://forms.gle/s9Y2tSZwULWeRkJi8"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        changePassword = view.findViewById(R.id.change_password)
        changePassword.setOnClickListener{
            showBelumTersedia(view)
        }

        settingsButton = view.findViewById(R.id.settings)
        settingsButton.setOnClickListener{
            showBelumTersedia(view)
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

    private fun showBelumTersedia(view: View){
        Toast.makeText(view.context, "Fitur ini belum tersedia!", Toast.LENGTH_SHORT).show()
    }
}
