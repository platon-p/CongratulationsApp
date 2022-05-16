package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.database.AppDatabase
import io.reactivex.android.schedulers.AndroidSchedulers

class ShowCardActivity : AppCompatActivity() {
    lateinit var titleTextView: TextView
    private var cardId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_card)

        titleTextView = findViewById(R.id.showCardTitle)
        cardId = intent.getIntExtra("CardId", 0).toLong()
        loadTitle()

        // TODO: Add card form (fragment or layout)
    }

    fun delete() {
        // TODO: Delete card by id
    }

    fun save() {
        // TODO: validate fields & save
        // TODO: show toast if success
    }

    @SuppressLint("CheckResult")
    private fun loadTitle() {
        AppDatabase.getDatabase(applicationContext)
            .cardDao().getById(cardId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    titleTextView.text = it.name
                },
                { finish() })
    }
}