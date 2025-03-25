package com.example.steptracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "StepTracker.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "ime"
        const val COLUMN_STEPS = "broj_koraka"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_STEPS INTEGER NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addUser(name: String, steps: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_STEPS, steps)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getSuccessfulUsers(): List<String> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_NAME FROM $TABLE_NAME WHERE $COLUMN_STEPS = 10", null)
        val users = mutableListOf<String>()

        if (cursor.moveToFirst()) {
            do {
                users.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return users
    }

    fun clearDatabase() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.close()
    }

    fun isTableEmpty(): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null)
        var isEmpty = true

        if (cursor.moveToFirst()) {
            isEmpty = cursor.getInt(0) == 0
        }

        cursor.close()
        db.close()
        return isEmpty
    }

    fun getSuccessfulUsersWithSteps(): List<Pair<String, Int>> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_NAME, $COLUMN_STEPS FROM $TABLE_NAME WHERE $COLUMN_STEPS >= 10", null)
        val users = mutableListOf<Pair<String, Int>>()

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(0)
                val steps = cursor.getInt(1)
                users.add(Pair(name, steps))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return users
    }


}
