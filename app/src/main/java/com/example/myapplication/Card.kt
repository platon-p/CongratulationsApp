package com.example.myapplication

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Card(
    @SerializedName("preset") val preset: Preset,
    @SerializedName("name") var name: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("fio") var fio: String
) : Serializable {
    @SerializedName("imagePath")
    lateinit var imagePath: String

    @SerializedName("pdfPath")
    lateinit var pdfPath: String

    // TODO: get image and pdf from api and save locally
}