// NotesAdapter.kt

package com.example.remind_me

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private val context: Context, private var notes: List<Note>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
        private val db:NotesDatabaseHelper = NotesDatabaseHelper(context)

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.note_items, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.titleTextView.text = currentNote.header
        holder.dateTextView.text = currentNote.date
        holder.timeTextView.text = currentNote.time
        holder.descriptionTextView.text = currentNote.description

        holder.updateButton.setOnClickListener {
            val intent = Intent(context, UpdateNoteActivity::class.java).apply {
                putExtra("noteId", currentNote.id)
            }
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {

            db.deleteNote(currentNote.id)
            refreshData(db.getAllNotes())
            Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
