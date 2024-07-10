package com.sofeed.myapp

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PilihHewan : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var button: Button
    private lateinit var bkMin : EditText
    private lateinit var abuMin : EditText
    private lateinit var pkMin : EditText
    private lateinit var lkMin : EditText
    private lateinit var skMin : EditText
    private lateinit var tdnMin : EditText
    private lateinit var caMin : EditText
    private lateinit var pMin : EditText
    private lateinit var betnMin : EditText
    private lateinit var metanaMin : TextView
    private lateinit var bkMax : EditText
    private lateinit var abuMax : EditText
    private lateinit var pkMax : EditText
    private lateinit var lkMax : EditText
    private lateinit var skMax : EditText
    private lateinit var tdnMax : EditText
    private lateinit var caMax : EditText
    private lateinit var pMax : EditText
    private lateinit var betnMax : EditText
    private lateinit var metanaMax : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pilih_hewan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val items = listOf("Sapi Pedaging", "Sapi Perah", "Kambing", "Domba")

        val autoComplete : AutoCompleteTextView = findViewById(R.id.autoComplete)

        val adapter = ArrayAdapter(this, R.layout.jenis_hewan, items)
        autoComplete.setAdapter(adapter)

        val hewan = DoubleArray(18)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            val pilihan : String
            if (itemSelected == "Sapi Pedaging"){
                pilihan = "0"
            }else if(itemSelected == "Sapi Perah"){
                pilihan = "1"
            }else if(itemSelected == "Kambing"){
                pilihan = "3"
            }else{
                pilihan = "2"
            }

            val db = Firebase.firestore

            db.collection("hewan").document(pilihan).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        hewan[0] = document.getDouble("bahan_kering_min") ?: 0.0
                        hewan[1] = document.getDouble("abu_min") ?: 0.0
                        hewan[2] = document.getDouble("pk_min") ?: 0.0
                        hewan[3] = document.getDouble("lk_min") ?: 0.0
                        hewan[4] = document.getDouble("sk_min") ?: 0.0
                        hewan[5] = document.getDouble("betn_min") ?: 0.0
                        hewan[6] = document.getDouble("tdn_min") ?: 0.0
                        hewan[7] = document.getDouble("ca_min") ?: 0.0
                        hewan[8] = document.getDouble("p_min") ?: 0.0
                        hewan[9] = document.getDouble("bahan_kering_max") ?: 0.0
                        hewan[10] = document.getDouble("abu_max") ?: 0.0
                        hewan[11] = document.getDouble("pk_max") ?: 0.0
                        hewan[12] = document.getDouble("lk_max") ?: 0.0
                        hewan[13] = document.getDouble("sk_max") ?: 0.0
                        hewan[14] = document.getDouble("betn_max") ?: 0.0
                        hewan[15] = document.getDouble("tdn_max") ?: 0.0
                        hewan[16] = document.getDouble("ca_max") ?: 0.0
                        hewan[17] = document.getDouble("p_max") ?: 0.0
                        Log.d("FireBase", "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d("FireBase", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("FireBase", "get failed with ", exception)
                }



            Toast.makeText(this, "Item: $itemSelected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dengarkanSaya()
}