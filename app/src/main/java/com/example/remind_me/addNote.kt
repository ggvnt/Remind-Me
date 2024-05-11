package com.example.remind_me

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.remind_me.databinding.ActivityAddNoteBinding

class addNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = NotesDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val header = binding.header.text.toString()
            val date = binding.editTextDate.text.toString()
            val time = binding.editTextTime.text.toString()
            val description = binding.description.text.toString()

            val note = Note(0, header, date, time, description)
            db.insertNote(note)
            finish()
            Toast.makeText(this, "Note Saved" , Toast.LENGTH_LONG).show()
        }
    }
}
