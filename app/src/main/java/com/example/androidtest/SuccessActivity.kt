package com.example.androidtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtest.R.*
import com.example.steptracker.DatabaseHelper


import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class SuccessActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var listView: ListView
    private lateinit var btnClearData: Button
    private lateinit var dbHelper: DatabaseHelper

    lateinit var editTextMessage: EditText
    lateinit var sendSMSButton: Button
    lateinit var radioGroup: RadioGroup
    lateinit var radioButtonPhone1: RadioButton
    lateinit var radioButtonPhone2: RadioButton
    lateinit var radioButtonPhone3: RadioButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_success)

        textView = findViewById(id.textView)
        listView = findViewById(id.listView)
        btnClearData = findViewById(id.btnClearData)
        dbHelper = DatabaseHelper(this)

        loadUsers()

        btnClearData.setOnClickListener {
            dbHelper.clearDatabase()
            loadUsers()
            Toast.makeText(this, "Popis korisnika je obrisan", Toast.LENGTH_SHORT).show()
        }

        textView = findViewById(R.id.textView)
        editTextMessage = findViewById(R.id.textView)
        sendSMSButton = findViewById(R.id.buttonSendSMS)
        radioGroup = findViewById(R.id.radioGroup)
        radioButtonPhone1 = findViewById(R.id.radioButtonPhone1)
        radioButtonPhone2 = findViewById(R.id.radioButtonPhone2)
        radioButtonPhone3 = findViewById(R.id.radioButtonPhone3)

        val name = intent.getStringExtra("name")
        textView.text = "$name, uspješno ste došli do 10 koraka."

        sendSMSButton.setOnClickListener {
            sendSMS()
        }
    }

    private fun sendSMS() {
        val selectedPhoneNumber = when (radioGroup.checkedRadioButtonId) {
            R.id.radioButtonPhone1 -> radioButtonPhone1.text.toString()
            R.id.radioButtonPhone2 -> radioButtonPhone2.text.toString()
            R.id.radioButtonPhone3 -> radioButtonPhone3.text.toString()
            else -> {
                Toast.makeText(this, "Molimo odaberite broj telefona!", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val message = editTextMessage.text.toString()

        if (message.isEmpty()) {
            Toast.makeText(this, "Unesite tekst poruke!", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("sms:$selectedPhoneNumber")
            putExtra("sms_body", message)
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Ne mogu pokrenuti SMS aplikaciju!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUsers() {
        val users = dbHelper.getSuccessfulUsersWithSteps() // Mora vraćati List<Pair<String, Int>>
        textView.text = if (users.isEmpty()) {
            "Nema korisnika koji su postigli 10 koraka."
        } else {
            "Korisnici koji su postigli 10 koraka:"
        }

        val adapter = UserListAdapter(this, users)
        listView.adapter = adapter
    }


}
