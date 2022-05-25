package com.example.myapplication.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Preset(
    @Expose
    @SerializedName("id") var id: Int,

    @Expose
    @SerializedName("name") var name: String,

    @Expose
    @SerializedName("paperSize") var paperSize: String,

    @Expose
    @SerializedName("text") var text: String,

    @Expose
    @SerializedName("greeting") var greeting: String,

    @Expose
    @SerializedName("textX") var textX: Float,

    @Expose
    @SerializedName("textY") var textY: Float,

    @Expose
    @SerializedName("greetingY") var greetingY: Float,

    @Expose
    @SerializedName("image") var image: String,
) : Serializable