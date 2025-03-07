package com.example.androidtest

import android.R.attr.value
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
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
            if (counter > 10) {
                val intent = Intent(this@MainActivity, SuccessActivity::class.java);
                val name: EditText = findViewById(R.id.plainTextName)
                val nameText = name.text.toString()
                intent.putExtra("counter", counter)
                intent.putExtra("name", nameText)
                startActivity(intent);
            }
        }

        sub_b.setOnClickListener() {
            counter--;
            refresh();
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.restore_counter -> {
                // kod za 19. zadatak
                true
            }
        }
        return super.onOptionsItemSelected(item)
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