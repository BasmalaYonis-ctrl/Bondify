package com.example.newbondify.network

import TruthOrDareApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.truthordarebot.xyz/"

    val api: TruthOrDareApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TruthOrDareApi::class.java)
    }
}