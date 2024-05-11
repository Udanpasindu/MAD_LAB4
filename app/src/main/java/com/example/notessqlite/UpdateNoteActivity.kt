package com.example.notessqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notessqlite.databinding.ActivityAddNoteBinding
import com.example.notessqlite.databinding.ActivityUpdateBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_update)

        db = NotesDatabaseHelper(this)
    }
}