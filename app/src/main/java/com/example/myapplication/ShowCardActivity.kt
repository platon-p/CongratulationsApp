package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ShowCardActivity : AppCompatActivity() {
    lateinit var titleTextView: TextView
    lateinit var card: Card
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_card)

        titleTextView = findViewById(R.id.showCardTitle)
        card = intent.getSerializableExtra("Card") as Card
        titleTextView.text = card.name
    }
}