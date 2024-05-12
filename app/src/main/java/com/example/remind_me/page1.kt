package com.example.remind_me

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remind_me.databinding.ActivityPage1Binding
import androidx.recyclerview.widget.RecyclerView



class page1 : AppCompatActivity() {

    private lateinit var binding: ActivityPage1Binding
    private lateinit var db: NotesDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_page1)

        binding = ActivityPage1Binding.inflate(layoutInflater)
        setContentView(binding.root)


        db = NotesDatabaseHelper(this)
        notesAdapter = NotesAdapter(this, db.getAllNotes())

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this,addNote::class.java)
            startActivity(intent)
        }


    }
    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }

}