package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley

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
        Log.println(Log.INFO, "AAAA", preset.id.toString())
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
        // TODO: save card in ROOM
        val intentt = Intent(this, ShowCardActivity::class.java)
        intentt.putExtra(
            "CardId",
            1 // TODO
        )
        finish()
        startActivity(intentt)
    }

    fun insertCard() {
        // TODO: Get data from form & add to db
        // TODO: save pdf
//        AppDatabase
//            .getDatabase(requireContext())
//            .cardDao()
//            .insert(
//                Card(
//                    1, 1,
//                    "Моя открытка", "Мужской", "Иванов Иван Иванович"
//                )
//            )
//            .subscribeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { Toast.makeText(context, "SUCCESS!", Toast.LENGTH_SHORT).show() },
//                {
//                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
//                })
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