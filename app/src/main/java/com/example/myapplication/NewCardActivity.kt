package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.api.ApiClient
import com.example.myapplication.api.Preset
import com.example.myapplication.api.PresetApi
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.Card
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
//        generatePDF()
        insertCard()
    }

    private fun changeActivity(card: Card) {
        val intent = Intent(this, ShowCardActivity::class.java)
        intent.putExtra("Card", card)
        finish()
        startActivity(intent)
    }

//    private fun generatePDF(preset: Preset, name: String, fio: String, gender: String): URI? {
//        val dirPath = Environment.getExternalStorageDirectory().toString() + "/results"
//        val folder = File(dirPath)
//        if (!folder.exists()) folder.mkdirs()
//
//        val newFile = File(dirPath, System.currentTimeMillis().toString() + ".pdf")
//        return null
//    }

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
        val api = ApiClient.getApiClient().create(PresetApi::class.java)
        api.getBackground(preset.image).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val bytes = response.body()?.bytes()!!
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Retrofit", t.toString())
            }
        })
    }
}