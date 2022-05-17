package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.database.AppDatabase
import io.reactivex.android.schedulers.AndroidSchedulers

class ShowCardActivity : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var cardNameInput: EditText
    private lateinit var userNameInput: EditText
    private lateinit var deleteButton: Button
    private lateinit var saveButton: Button
    private lateinit var shareButton: Button

    private var cardId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_card)

        cardId = intent.getIntExtra("CardId", 0).toLong()


        titleTextView = findViewById(R.id.showCardTitle)

        cardNameInput = findViewById(R.id.editTextCardName)
        userNameInput = findViewById(R.id.editTextPersonName)

        deleteButton = findViewById(R.id.deleteButton)
        deleteButton.setOnClickListener { delete() }

        saveButton = findViewById(R.id.saveButton)
        saveButton.setOnClickListener { save() }

        shareButton = findViewById(R.id.shareButton)
        shareButton.setOnClickListener { share() }

        initFields()

        // TODO: Add card form (fragment or layout)
    }

    private fun share() {
        // TODO("Share pdf as file or as link")
    }

    private fun delete() {
        // TODO: Delete card by id
        // TODO: Delete file
    }

    private fun save() {
        // TODO: validate fields & save
        // TODO: show toast if success
    }

    @SuppressLint("CheckResult")
    private fun initFields() {
        AppDatabase.getDatabase(applicationContext)
            .cardDao().getById(cardId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    titleTextView.text = it.name
                    cardNameInput.setText(it.name)
                    userNameInput.setText(it.fio)
                },
                { finish() })
    }
}