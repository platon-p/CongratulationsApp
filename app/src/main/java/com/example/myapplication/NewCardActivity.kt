package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NewCardActivity : AppCompatActivity() {
    lateinit var submitButton: Button
    lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_card)

        supportActionBar?.title = (intent.getSerializableExtra("Preset") as? Preset)?.name
        submitButton = findViewById(R.id.createButton)
        editText = findViewById(R.id.editTextPersonName)
        loadImage()
    }

    private fun loadImage() {
//        TODO("Get image from api by path in Preset.image")
//        TODO("Hide preloader")
    }
}