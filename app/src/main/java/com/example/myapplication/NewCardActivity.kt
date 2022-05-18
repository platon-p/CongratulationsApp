package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.Card
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class NewCardActivity : AppCompatActivity() {
    lateinit var createButton: Button
    lateinit var genderRadio: RadioGroup
    lateinit var usernameInput: EditText
    lateinit var cardnameInput: EditText
    lateinit var preset: Preset
    lateinit var imageView: ImageView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_card)

        preset = intent.getSerializableExtra("Preset") as Preset
        supportActionBar?.title = preset.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        createButton = findViewById(R.id.createButton)
        createButton.setOnClickListener { onSubmit() }

        usernameInput = findViewById(R.id.editTextPersonName)

        cardnameInput = findViewById(R.id.editTextCardName)

        genderRadio = findViewById(R.id.genderRadioGroup)

        imageView = findViewById(R.id.imageView)
        progressBar = findViewById(R.id.newCardProgressBar)
        loadImage()
    }

    private fun onSubmit() {
        var errors = false
        if (cardnameInput.text?.trim().toString() == "") {
            errors = true
            cardnameInput.error = "Поле должно быть заполнено"
        }
        if (usernameInput.text?.trim().toString() == "") {
            errors = true
            usernameInput.error = "Поле должно быть заполнено"
        }
        if (errors) {
            Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
            return
        }
        insertCard()
    }

    private fun changeActivity(card: Card) {
        val intent = Intent(this, ShowCardActivity::class.java)
        intent.putExtra("Card", card)
        finish()
        startActivity(intent)
    }

    @SuppressLint("CheckResult")
    fun insertCard() {
        // TODO: save pdf
        val card = Card()
        card.name = cardnameInput.text.toString()
        card.presetId = preset.id
        card.fio = usernameInput.text.toString()
        card.gender =
            findViewById<RadioButton>(genderRadio.checkedRadioButtonId).text.toString()

        val exec: Executor = Executors.newSingleThreadExecutor()
        exec.execute {
            card.id = AppDatabase.getDatabase(applicationContext).cardDao().getCount() + 1

            AppDatabase.getDatabase(applicationContext).cardDao().insert(card)
                .subscribe { changeActivity(card) }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun loadImage() {
        val url = resources.getString(R.string.base_api_url) + "/" + preset.image
        val queue = Volley.newRequestQueue(this)

        val request = ImageRequest(url,
            { bitmap ->
                imageView.setImageBitmap(bitmap)
                progressBar.visibility = View.GONE
            },
            imageView.maxWidth,
            imageView.maxHeight,
            imageView.scaleType,
            null,
            {
                Log.println(Log.ERROR, "Error NewCardActivity", it.toString())
            }
        )
        queue.add(request)
    }
}