package com.example.androidtest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    private var counter = 0;
    private lateinit var display: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Toast.makeText(applicationContext, "onCreate", Toast.LENGTH_SHORT).show();
        Log.i("MyLog", "onCreate");
    }

    override fun onStart() {
        super.onStart();
        Toast.makeText(applicationContext, "onStart", Toast.LENGTH_SHORT).show();
        Log.i("MyLog", "onStart");

        Toast.makeText(applicationContext, "onStart", Toast.LENGTH_SHORT).show();
        Log.v("MyLog", "onStartVerbose");

        Toast.makeText(applicationContext, "onStart", Toast.LENGTH_SHORT).show();
        Log.d("MyLog", "onStartDebug");

        Toast.makeText(applicationContext, "onStart", Toast.LENGTH_SHORT).show();
        Log.w("MyLog", "onStartWarning");

        Toast.makeText(applicationContext, "onStart", Toast.LENGTH_SHORT).show();
        Log.e("MyLog", "onStartError");

        display = findViewById(R.id.counterText)
        val add_b: Button = findViewById(R.id.add_b);
        val sub_b: Button = findViewById(R.id.sub_b);

        add_b.setOnClickListener() {
            counter++;
            refresh();
        }

        sub_b.setOnClickListener() {
            counter--;
            refresh();
        }

    }

    fun refresh() {
        display.text = counter.toString();
        val prefs = getPreferences(MODE_PRIVATE).edit();
        prefs.putInt("counter", counter);
        prefs.apply();
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(applicationContext, "onResume", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(applicationContext, "onPause", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(applicationContext, "onStop", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(applicationContext, "onDestroy", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(applicationContext, "onRestart", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onRestart")
    }
}