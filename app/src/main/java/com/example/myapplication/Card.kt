package com.example.myapplication

import java.net.URI

class Card(var preset: Preset, var gender: String, var fio: String) {
    var url = URI.create((R.string.base_api_url + R.string.api_pdf_path).toString())
}