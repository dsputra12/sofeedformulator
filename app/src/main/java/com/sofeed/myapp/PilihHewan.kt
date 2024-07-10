package com.sofeed.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PilihHewan : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var bkMin: EditText
    private lateinit var abuMin: EditText
    private lateinit var pkMin: EditText
    private lateinit var lkMin: EditText
    private lateinit var skMin: EditText
    private lateinit var tdnMin: EditText
    private lateinit var caMin: EditText
    private lateinit var pMin: EditText
    private lateinit var betnMin: EditText
    private lateinit var bkMax: EditText
    private lateinit var abuMax: EditText
    private lateinit var pkMax: EditText
    private lateinit var lkMax: EditText
    private lateinit var skMax: EditText
    private lateinit var tdnMax: EditText
    private lateinit var caMax: EditText
    private lateinit var pMax: EditText
    private lateinit var betnMax: EditText
    private var hewan = DoubleArray(18)
    private var isUpdating = false

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pilih_hewan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bkMin = findViewById(R.id.BKValue)
        bkMax = findViewById(R.id.BKValueMax)
        abuMin = findViewById(R.id.AbuValue)
        abuMax = findViewById(R.id.AbuValueMax)
        pkMin = findViewById(R.id.PKValue)
        pkMax = findViewById(R.id.PKValueMax)
        lkMin = findViewById(R.id.LKValue)
        lkMax = findViewById(R.id.LKValueMax)
        skMin = findViewById(R.id.SKValue)
        skMax = findViewById(R.id.SKValueMax)
        betnMin = findViewById(R.id.BETNValue)
        betnMax = findViewById(R.id.BETNValueMax)
        tdnMin = findViewById(R.id.TDNValue)
        tdnMax = findViewById(R.id.TDNValueMax)
        caMin = findViewById(R.id.CAValue)
        caMax = findViewById(R.id.CAValueMax)
        pMin = findViewById(R.id.PValue)
        pMax = findViewById(R.id.PValueMax)

        val items = listOf("Sapi Pedaging", "Sapi Perah", "Kambing", "Domba")
        val autoComplete: AutoCompleteTextView = findViewById(R.id.autoComplete)
        val adapter = ArrayAdapter(this, R.layout.jenis_hewan, items)
        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            val pilihan: String = when (itemSelected) {
                "Sapi Pedaging" -> "0"
                "Sapi Perah" -> "1"
                "Kambing" -> "3"
                else -> "2"
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
                        updateEditTexts()
                    } else {
                        Log.d("FireBase", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("FireBase", "get failed with ", exception)
                }
            Toast.makeText(this, "Item: $itemSelected", Toast.LENGTH_SHORT).show()
        }

        addTextWatchers()

        button = findViewById(R.id.SelectPakan)
        button.setOnClickListener {
            val intent = Intent(this, LakukanFormulasi::class.java)
            intent.putExtra("hewan", hewan)
            startActivity(intent)
        }
    }

    private fun addTextWatchers() {
        addTextWatcher(bkMin, 0)
        addTextWatcher(abuMin, 1)
        addTextWatcher(pkMin, 2)
        addTextWatcher(lkMin, 3)
        addTextWatcher(skMin, 4)
        addTextWatcher(betnMin, 5)
        addTextWatcher(tdnMin, 6)
        addTextWatcher(caMin, 7)
        addTextWatcher(pMin, 8)
        addTextWatcher(bkMax, 9)
        addTextWatcher(abuMax, 10)
        addTextWatcher(pkMax, 11)
        addTextWatcher(lkMax, 12)
        addTextWatcher(skMax, 13)
        addTextWatcher(betnMax, 14)
        addTextWatcher(tdnMax, 15)
        addTextWatcher(caMax, 16)
        addTextWatcher(pMax, 17)
    }

    private fun addTextWatcher(editText: EditText, position: Int) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            @SuppressLint("DefaultLocale")
            override fun afterTextChanged(s: Editable?) {
                if (!isUpdating) {
                    isUpdating = true
                    try {
                        val value = s.toString().toDoubleOrNull()
                        if (value != null) {
                            editText.removeTextChangedListener(this)
                            editText.setText(String.format("%.2f", value))
                            editText.setSelection(editText.text.length)
                            hewan[position] = value
                            editText.addTextChangedListener(this)
                        }
                    } catch (e: NumberFormatException) {
                        editText.error = "Invalid number"
                    }
                    isUpdating = false
                }
            }
        })
    }

    @SuppressLint("DefaultLocale")
    private fun updateEditTexts() {
        isUpdating = true
        bkMin.setText(String.format("%.2f", hewan[0]))
        abuMin.setText(String.format("%.2f", hewan[1]))
        pkMin.setText(String.format("%.2f", hewan[2]))
        lkMin.setText(String.format("%.2f", hewan[3]))
        skMin.setText(String.format("%.2f", hewan[4]))
        betnMin.setText(String.format("%.2f", hewan[5]))
        tdnMin.setText(String.format("%.2f", hewan[6]))
        caMin.setText(String.format("%.2f", hewan[7]))
        pMin.setText(String.format("%.2f", hewan[8]))
        bkMax.setText(String.format("%.2f", hewan[9]))
        abuMax.setText(String.format("%.2f", hewan[10]))
        pkMax.setText(String.format("%.2f", hewan[11]))
        lkMax.setText(String.format("%.2f", hewan[12]))
        skMax.setText(String.format("%.2f", hewan[13]))
        betnMax.setText(String.format("%.2f", hewan[14]))
        tdnMax.setText(String.format("%.2f", hewan[15]))
        caMax.setText(String.format("%.2f", hewan[16]))
        pMax.setText(String.format("%.2f", hewan[17]))
        isUpdating = false
    }
}
