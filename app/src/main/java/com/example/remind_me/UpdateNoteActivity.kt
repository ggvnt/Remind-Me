package com.example.remind_me

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.remind_me.databinding.ActivityUpdateBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        noteId = intent.getIntExtra("noteId", -1)

        if (noteId == -1) {
            // Handle the case when the note ID is not provided
            Toast.makeText(this, "Invalid note ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val note = db.getNoteById(noteId)

        if (note != null) {
            binding.updateHeader.setText(note.header)
            binding.updateTextDate.setText(note.date)
            binding.updateTextTime.setText(note.time)
            binding.updateDescription.setText(note.description)

            binding.updateSaveButton.setOnClickListener {
                val newHeader = binding.updateHeader.text.toString()
                val newDate = binding.updateTextDate.text.toString()
                val newTime = binding.updateTextTime.text.toString()
                val newDescription = binding.updateDescription.text.toString()

                if (newHeader.isNotEmpty() && newDate.isNotEmpty() && newTime.isNotEmpty() && newDescription.isNotEmpty()) {
                    val updatedNote = Note(noteId, newHeader, newDate, newTime, newDescription)
                    db.updateNote(updatedNote)

                    Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Handle the case when the note with the given ID doesn't exist
            Toast.makeText(this, "Invalid note ID", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
