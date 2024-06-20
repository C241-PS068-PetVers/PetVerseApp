package com.capstone.petverse.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DetectionRetrofitClient {
    private const val BASE_URL = "https://petverse-ml-ijvafhlghq-et.a.run.app/"

    val apiService: DetectionApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DetectionApiService::class.java)
    }
}