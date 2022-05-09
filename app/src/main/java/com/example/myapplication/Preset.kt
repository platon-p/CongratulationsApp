package com.example.myapplication

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Preset(
    @SerializedName("Name") var name: String,
    @SerializedName("PaperSize") var paperSize: String,
    @SerializedName("Text") var text: String,
    @SerializedName("Greeting") var greeting: String,
    @SerializedName("TextX") var textX: Float,
    @SerializedName("TextY") var textY: Float,
    @SerializedName("GreetingY") var greetingY: Float,
    @SerializedName("Image") var image: String,
) : Serializable