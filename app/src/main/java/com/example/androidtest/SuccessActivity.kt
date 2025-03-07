package com.example.androidtest

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SuccessActivity : AppCompatActivity() {

    private val spinner: Spinner? = null
    private var counter = 0;
    private var name = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_success)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        /*
        val backbut = findViewById<Button>(R.id.backbut)
        backbut.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        */

        var spinner: Spinner? = null
        spinner = this.spinner


        val sub1: Button = findViewById<Button>(R.id.sendSmsButton)
        sub1.setOnClickListener {
            val data: String = "Num: $counter, $name";
            startActivity(getSendSmsIntent("0977510243", data))

        }
    }

    override fun onStart() {
        super.onStart();
        val bundle = intent.extras;
        val value = bundle!!.getInt("counter");
        val value2 = bundle!!.getString("name");
        counter = value;
        if (value2 != null) {
            name = value2
        }

        val sendButton: Button = findViewById(R.id.sendSmsButton);
        /*sendButton.setOnClickListener() {
            val num = "0977510243";
            val msg = "Num: $counter, Name: $name";

            val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smto:$num");
                putExtra("sms_body", msg);
            }
        }*/

    }

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        val backbut = findViewById<Button>(R.id.backbut)
        backbut.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        var spinner: Spinner? = null
        spinner = this.spinner


        val sub1: Button = findViewById<Button>(R.id.sub1)
        sub1.setOnClickListener {
            val data: String = "Num: $counter, Name: $name";
            startActivity(getSendSmsIntent("1234567", data))

        }
    }*/
// textview_selected!!.text = "Selected : "+ Spinner [position]


    private fun getSendSmsIntent(phoneNumber: String, content: String?): Intent? {

        val uri = Uri.parse("smsto:$phoneNumber")
        val intent = Intent(Intent.ACTION_SENDTO, uri)

        intent.putExtra("sms_body", content)

        return getIntent(intent, true)
    }
    private fun getIntent(intent: Intent, isNewTask: Boolean): Intent? {
        return if (isNewTask) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) else intent

    }

    private fun sendSMS(phoneNumber: String, message: String) {
        val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), 0)
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, sentPI, null)
    }
}