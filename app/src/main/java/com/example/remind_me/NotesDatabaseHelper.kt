package com.example.remind_me

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "remindme.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TIME = "time"
        private const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_TITLE TEXT, " +
                "$COLUMN_DATE TEXT, " +
                "$COLUMN_TIME TEXT, " +
                "$COLUMN_DESCRIPTION TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNote(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.header)
            put(COLUMN_DATE, note.date)
            put(COLUMN_TIME, note.time)
            put(COLUMN_DESCRIPTION, note.description)
        }
        db.insert(TABLE_NAME, null, values)
    }
}
