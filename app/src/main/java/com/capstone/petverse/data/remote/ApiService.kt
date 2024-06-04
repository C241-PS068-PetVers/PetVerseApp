package com.capstone.petverse.data.remote

import android.annotation.SuppressLint
import com.capstone.petverse.data.model.LoginResponse
import com.capstone.petverse.data.model.LoginUser
import com.capstone.petverse.data.model.SignupResponse
import com.capstone.petverse.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("users")
    suspend fun signup(@SuppressLint("RestrictedApi") @Body user: User): Response<SignupResponse>

    @POST("login")
    suspend fun login(@Body user: LoginUser): Response<LoginResponse>
}
