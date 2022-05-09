package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley

class NewCardActivity : AppCompatActivity() {
    lateinit var submitButton: Button
    lateinit var editText: EditText
    lateinit var preset: Preset
    lateinit var imageView: ImageView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_card)

        preset = intent.getSerializableExtra("Preset") as Preset
        supportActionBar?.title = preset.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        submitButton = findViewById(R.id.createButton)
        editText = findViewById(R.id.editTextPersonName)
        imageView = findViewById(R.id.imageView)
        progressBar = findViewById(R.id.newCardProgressBar)
        loadImage()
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
//        TODO("Get image from api by path in Preset.image")
//        TODO("Hide preloader")
    }
}