package com.sofeed.myapp

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignIn : AppCompatActivity() {
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var signInButton: Button
    private lateinit var googleButton: ImageButton
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var backButton : ImageView
    private lateinit var signUpButton: TextView

    private var firebaseAuth = FirebaseAuth.getInstance()

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        } else {
            // Handle the sign-in failure if necessary
            Toast.makeText(this, "Sign-in failed", Toast.LENGTH_SHORT).show()
        }
    }


//    override fun onStart() {
//        super.onStart()
//        if(firebaseAuth.currentUser!=null){
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editEmail = findViewById(R.id.email)
        editPassword  = findViewById(R.id.password)
        signInButton = findViewById(R.id.signInButton)
        googleButton = findViewById(R.id.googleButton)
        backButton = findViewById(R.id.backButton)
        signUpButton = findViewById(R.id.signUpButton)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton.setOnClickListener {
            if(editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()){
                loginUser()
            }
            else{
                Toast.makeText(this, "Silahkan lengkapi email dan password anda", LENGTH_SHORT).show()
            }
        }
        googleButton.setOnClickListener{
            googleSignInClient.signOut().addOnCompleteListener{
                val signInIntent = googleSignInClient.signInIntent
                googleSignInLauncher.launch(signInIntent)
            }
        }
        backButton.setOnClickListener{
            finish()
        }
        signUpButton.setOnClickListener{
            startActivity(Intent(this, SignUp::class.java))
        }
    }

    private fun loginUser(){
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                startActivity(Intent(this, Homepage::class.java))
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, LENGTH_SHORT).show()
            }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            e.printStackTrace()
            Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, Homepage::class.java))
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(applicationContext, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}