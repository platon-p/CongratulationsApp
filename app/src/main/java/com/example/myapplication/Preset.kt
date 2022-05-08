package com.example.myapplication

import java.io.Serializable

class Presett(
    var name: String,
    var paperSize: String,
    var text: String,
    var greeting: String,
    var textX: Float,
    var textY: Float,
    var greetingY: Float,
    var image: String,
)

class Preset(var name: String) : Serializable