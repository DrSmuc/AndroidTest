package com.example.androidtest

import android.R.attr.value
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.steptracker.DatabaseHelper
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private var counter = 0;
    private lateinit var display: TextView;
    private lateinit var dbHelper: DatabaseHelper;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var dbHelper = DatabaseHelper(this)

        if (dbHelper.isTableEmpty()) {
            dbHelper.addUser("Mirko", 10)
            dbHelper.addUser("Slavko", 10)
            dbHelper.addUser("Caco", 10)
            Log.i("MyLog", "Podaci dodani u bazu jer je bila prazna")
        } else {
            Log.i("MyLog", "Baza već sadrži podatke, nije dodano ništa")
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
            if (counter >= 10) {
                var dbHelper = DatabaseHelper(this)
                val intent = Intent(this@MainActivity, SuccessActivity::class.java);
                val name: EditText = findViewById(R.id.plainTextName)
                dbHelper.addUser(name.text.toString(), counter.toInt())
                val nameText = name.text.toString()
                intent.putExtra("counter", counter)
                intent.putExtra("name", nameText)
                startActivity(intent);
            }
        }

        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val savedName = sharedPreferences.getString("user_name", "")
        val savedCount = sharedPreferences.getInt("counter", 0)
        val name: EditText = findViewById(R.id.plainTextName)
        name.setText(savedName)
        counter = savedCount

        display.text = counter.toString();

        sub_b.setOnClickListener() {
            counter--;
            refresh();
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("COUNTER_VALUE", counter)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState.getInt("COUNTER_VALUE", 0)
        display.text = counter.toString()
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

        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val name: EditText = findViewById(R.id.plainTextName)
        editor.putString("user_name", name.text.toString())
        editor.putInt("counter", counter)
        editor.apply()

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.restore_counter -> {
                counter = 0
                display.text = counter.toString()
                return true
            }

            // Dodavanje opcija za promjenu jezika
            R.id.croatian -> {
                changeLanguage(this, "hr")  // Pozivanje funkcije za promjenu jezika na hrvatski
                Toast.makeText(this, "Jezik promijenjen na Hrvatski", Toast.LENGTH_SHORT).show()
                recreate()  // Ponovno učitaj aktivnost kako bi se primijenile promjene
                return true
            }
            R.id.english -> {
                changeLanguage(this, "en")  // Pozivanje funkcije za promjenu jezika na engleski
                Toast.makeText(this, "Language changed to English", Toast.LENGTH_SHORT).show()
                recreate()  // Ponovno učitaj aktivnost kako bi se primijenile promjene
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Suppress("DEPRECATION")
    fun changeLanguage(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        context.createConfigurationContext(config)  // Ovo osigurava da se promjena jezika primijeni
        res.updateConfiguration(config, res.displayMetrics)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_float, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reset_counter -> {
                counter = 0
                display.text = counter.toString()
                Toast.makeText(this, "Brojač resetiran!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

}