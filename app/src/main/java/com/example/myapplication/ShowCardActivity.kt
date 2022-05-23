package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.myapplication.api.ApiClient
import com.example.myapplication.api.Preset
import com.example.myapplication.api.PresetApi
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.Card
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
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

    private fun setTitleText(id: Int) {
        val api = ApiClient.getApiClient().create(PresetApi::class.java)
        api.getPreset(id).enqueue(object : Callback<Preset> {
            override fun onResponse(call: Call<Preset>, response: Response<Preset>) {
                titleTextView.text = response.body()?.name
            }

            override fun onFailure(call: Call<Preset>, t: Throwable) {
                Log.e("Set Title", t.toString())
            }
        })
    }

    private fun share() {
        val api = ApiClient.getApiClient().create(PresetApi::class.java)
        api.getPDF(card.presetId, card.gender, card.fio).enqueue(object : Callback<ResponseBody> {
            @SuppressLint("IntentReset")
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val file = File(filesDir, card.name + ".pdf")
                file.createNewFile()

                val fos = FileOutputStream(file)
                fos.write(response.body()?.bytes()!!)
                fos.close()

                val intent = Intent(Intent.ACTION_SEND)
                val uri = FileProvider.getUriForFile(
                    applicationContext,
                    applicationContext.packageName + ".provider",
                    file
                )
                intent.type = "application/pdf"
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                startActivity(Intent.createChooser(intent, "Поделиться открыткой"))
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Retrofit Error", t.toString())
            }
        })
    }

    private fun onDelete() {
        AlertDialog
            .Builder(this)
            .setTitle("Вы действительно хотите удалить открытку?")
            .setPositiveButton("Удалить") { dialog, _ ->
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
                    val f = File(filesDir, card.name + ".pdf")
                    if (f.exists()) f.delete()
                    val mIntent = Intent()
                    mIntent.putExtra("ToastText", "Удалено")
                    setResult(Activity.RESULT_OK, mIntent)
                    finish()
                }
        }
    }

    @SuppressLint("CheckResult")
    private fun save() {
        var error = false
        if (userNameInput.text.toString() == "") {
            error = true
            userNameInput.error = "Поле не заполнено!"
        }
        if (cardNameInput.text.toString() == "") {
            error = true
            cardNameInput.error = "Поле не заполнено!"
        }
        if (error) {
            Toast.makeText(applicationContext, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
            return
        }
        card.fio = userNameInput.text.toString().trim()
        card.name = cardNameInput.text.toString().trim()
        card.gender = findViewById<RadioButton>(genderRadio.checkedRadioButtonId).text.toString()
        Executors.newSingleThreadExecutor().execute {
            AppDatabase.getDatabase(applicationContext).cardDao().update(card).subscribe {
                val mIntent = Intent()
                mIntent.putExtra("ToastText", "Сохранено")
                setResult(Activity.RESULT_OK, mIntent)
                finish()
            }
        }
    }
}