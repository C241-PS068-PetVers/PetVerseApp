package com.capstone.petverse.data.remote

import com.capstone.petverse.data.response.LoginResponse
import com.capstone.petverse.data.response.SignupResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("/api/auth/register")
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<SignupResponse>

    @FormUrlEncoded
    @POST("/api/auth/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>
}
