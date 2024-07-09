package com.sofeed.myapp

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.firebase.auth.userProfileChangeRequest

class SignUp : AppCompatActivity() {
    private lateinit var editPersonName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var signUpButton: Button
    private lateinit var backButton: ImageView

    private var firebaseAuth = FirebaseAuth.getInstance()

//    override fun onStart() {
//        super.onStart()
//        if(firebaseAuth.currentUser!=null){
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editPersonName = findViewById(R.id.username)
        editEmail = findViewById(R.id.email)
        editPassword  = findViewById(R.id.password)
        signUpButton = findViewById(R.id.signUpButton)
        backButton = findViewById(R.id.backButton)

        signUpButton.setOnClickListener{
            if(editPersonName.text.isNotEmpty() && editPassword.text.isNotEmpty() && editEmail.text.isNotEmpty()){
                registerUser()
            }
            else{
                Toast.makeText(this,"Lengkapi dulu data Anda", LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener{
            finish()
        }
    }

    private fun registerUser(){
        val username = editPersonName.text.toString()
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    // Buat nsmpilin data user
                    val userUpdateProfile = userProfileChangeRequest {
                        displayName = username
                    }
                    val user = task.result.user
                    user!!.updateProfile(userUpdateProfile)
                        .addOnCompleteListener {
                            firebaseAuth.signOut() // Sign out the user
                            startActivity(Intent(this, SignIn::class.java)) // Redirect to the sign-in page
                            finish() // Close the sign-up activity
                        }
                        .addOnFailureListener { error2 ->
                            Toast.makeText(this, error2.localizedMessage, LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, LENGTH_SHORT).show()
            }
    }
}