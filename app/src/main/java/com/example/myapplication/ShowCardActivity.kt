package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.Card
import com.google.gson.Gson
import java.util.concurrent.Executors

class ShowCardActivity : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var cardNameInput: EditText
    private lateinit var userNameInput: EditText
    private lateinit var deleteButton: Button
    private lateinit var saveButton: Button
    private lateinit var shareButton: Button
    private lateinit var genderRadio: RadioGroup

    private lateinit var card: Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_card)

        card = intent.getSerializableExtra("Card") as Card

        titleTextView = findViewById(R.id.showCardTitle)
        setTitleText(card.presetId)

        cardNameInput = findViewById(R.id.editTextCardName)
        cardNameInput.setText(card.name)

        userNameInput = findViewById(R.id.editTextPersonName)
        userNameInput.setText(card.fio)

        genderRadio = findViewById(R.id.genderRadioGroup)
        genderRadio.check(if (card.gender == resources.getString(R.string.male_title)) R.id.maleRadioButton else R.id.femaleRadioButton)

        deleteButton = findViewById(R.id.deleteButton)
        deleteButton.setOnClickListener { onDelete() }

        saveButton = findViewById(R.id.saveButton)
        saveButton.setOnClickListener { save() }

        shareButton = findViewById(R.id.shareButton)
        shareButton.setOnClickListener { share() }
    }

    private fun setTitleText(id: Int): Preset? {
        val queue = Volley.newRequestQueue(applicationContext)
        queue.add(StringRequest(
            Request.Method.GET,
            resources.getString(R.string.base_api_url) + "/" + resources.getString(R.string.api_preset) + "?id=" + id.toString(),
            {
                val preset = Gson().fromJson(it.toString(), Preset::class.java)
                titleTextView.text = preset.name
            }, {})
        )
        return null
    }

    private fun share() {
        // TODO("Share pdf as file or as link")
    }

    private fun onDelete() {
        // TODO: Are you sure?
        AlertDialog
            .Builder(this)
            .setTitle("Вы действительно хотите удалить открытку?")
            .setPositiveButton("Удалить") { dialog, id ->
                delete()
                dialog.cancel()
            }.setNegativeButton("Отмена", null).show()
    }

    @SuppressLint("CheckResult")
    private fun delete() {
        Executors.newSingleThreadExecutor().execute {
            AppDatabase
                .getDatabase(applicationContext)
                .cardDao()
                .delete(card)
                .subscribe {
                    // TODO: Show toast if success
                    finish()
                }
        }
        // TODO: Delete file
    }

    private fun save() {
        // TODO: validate fields & save
        // TODO: show toast if success
    }
}