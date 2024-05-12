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

    fun getAllNotes(): List<Note> {
        val notesList = mutableListOf<Note>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val cursor = db.rawQuery(selectQuery, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

            val note = Note(id, title, date, time, description)
            notesList.add(note)
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun updateNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.header)
            put(COLUMN_DATE, note.date)
            put(COLUMN_TIME, note.time)
            put(COLUMN_DESCRIPTION, note.description)
        }
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(note.id.toString())
        db.update(TABLE_NAME, values, selection, selectionArgs)
        db.close()
    }

    fun getNoteById(id: Int): Note? {
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(id.toString()))

        var note: Note? = null
        cursor.use {
            if (it.moveToFirst()) {
                val noteId = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID))
                val title = it.getString(it.getColumnIndexOrThrow(COLUMN_TITLE))
                val date = it.getString(it.getColumnIndexOrThrow(COLUMN_DATE))
                val time = it.getString(it.getColumnIndexOrThrow(COLUMN_TIME))
                val description = it.getString(it.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

                note = Note(noteId, title, date, time, description)
            }
        }
        cursor.close()
        db.close()
        return note
    }

    fun deleteNote(id: Int) {
        val db = writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(id.toString())
        db.delete(TABLE_NAME, selection, selectionArgs)
        db.close()
    }


}
