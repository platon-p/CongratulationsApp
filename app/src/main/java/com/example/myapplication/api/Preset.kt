package com.example.myapplication.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Preset(
    @Expose
    @SerializedName("ID") var id: Int,

    @Expose
    @SerializedName("Name") var name: String,

    @Expose
    @SerializedName("PaperSize") var paperSize: String,

    @Expose
    @SerializedName("Text") var text: String,

    @Expose
    @SerializedName("Greeting") var greeting: String,

    @Expose
    @SerializedName("TextX") var textX: Float,

    @Expose
    @SerializedName("TextY") var textY: Float,

    @Expose
    @SerializedName("GreetingY") var greetingY: Float,

    @Expose
    @SerializedName("Image") var image: String,
) : Serializable