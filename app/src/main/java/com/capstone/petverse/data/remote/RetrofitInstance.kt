package com.capstone.petverse.data.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val gson = GsonBuilder()
    .setLenient()
    .create()

val retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.1.6:3000/")
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

val apiService = retrofit.create(ApiService::class.java)

