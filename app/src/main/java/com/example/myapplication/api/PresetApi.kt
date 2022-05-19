package com.example.myapplication.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PresetApi {
    @GET("api/presets")
    fun getAllPresets(): Call<List<Preset>>

    @GET("api/preset")
    fun getPreset(@Query("id") id: Int): Call<Preset>

    @GET("{path}")
    fun getBackground(@Path("path") path: String): Call<ResponseBody>

    @GET("api/pdf")
    fun getPDF(
        @Query("id") presetId: Int,
        @Query("gender") gender: String,
        @Query("name") fio: String
    ): Call<RequestBody>
}