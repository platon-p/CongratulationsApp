package com.example.myapplication.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {
    companion object {
        val baseUrl = "http://congratulationsp.ru/"
        var retrofit: Retrofit? = null
        fun getApiClient(): Retrofit {
            if (retrofit == null) {
                retrofit =
                    Retrofit.Builder().baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit as Retrofit
        }
    }
}