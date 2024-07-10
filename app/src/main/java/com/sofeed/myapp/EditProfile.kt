package com.sofeed.myapp

import android.os.Bundle
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.sofeed.myapp.databinding.ActivityProfileSheetBinding
import com.sofeed.myapp.databinding.FragmentEditProfileBinding


class EditProfile : Fragment() {
    private  lateinit var binding: FragmentEditProfileBinding
    private lateinit var BackButton: ImageButton
    private lateinit var displayUsername : EditText
    private lateinit var displayEmail: EditText
    private val firebaseAuth = FirebaseAuth.getInstance()
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

        val firebaseUser = firebaseAuth.currentUser

        displayUsername = view.findViewById(R.id.displayUsername)
        displayEmail = view.findViewById(R.id.displayEmail)
        if(firebaseUser != null){
            binding.displayUsername.setText(firebaseUser.displayName)
            binding.displayEmail.setText(firebaseUser.email)
        }

    }

    private fun showBottomSheet() {
        val editUsername =binding.displayUsername
        val editEmail = binding.displayEmail
        val newUsername = editUsername.text.toString().trim()
        val newEmail = editEmail.text.toString().trim()

        val sheetDialog = BottomSheetDialog(requireContext())
        val sheetBinding = ActivityProfileSheetBinding.inflate(layoutInflater)
        sheetDialog.apply {
            setContentView(sheetBinding.root)
            show()
        }

        sheetBinding.confirmButton.setOnClickListener {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newUsername)
                .build()

            val firebaseUser = firebaseAuth.currentUser
            firebaseUser?.updateEmail(newEmail)
                ?.addOnCompleteListener { emailUpdateTask ->
                    if (emailUpdateTask.isSuccessful) {
                        // Email update successful, now update display name
                        firebaseUser.updateProfile(profileUpdates)
                            .addOnCompleteListener { profileUpdateTask ->
                                if (profileUpdateTask.isSuccessful) {
                                    // Profile update successful
                                    firebaseUser.reload() // Reload the user object to reflect changes
                                    Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                                } else {
                                    // Profile update failed
                                    Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        // Email update failed
                        Toast.makeText(requireContext(), "Failed to update email", Toast.LENGTH_SHORT).show()
                    }
                }


            sheetDialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Show the bottom navigation bar
        (activity as? Homepage)?.showBottomNavigation()
    }
}