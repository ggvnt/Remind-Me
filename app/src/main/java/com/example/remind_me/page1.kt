package com.example.remind_me

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.remind_me.databinding.ActivityPage1Binding

class page1 : AppCompatActivity() {

    private lateinit var binding: ActivityPage1Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_page1)

        binding = ActivityPage1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            val intent = Intent(this,addNote::class.java)
            startActivity(intent)
        }


    }
}