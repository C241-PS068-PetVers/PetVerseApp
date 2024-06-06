package com.capstone.petverse.data.repository

import com.capstone.petverse.data.remote.ApiService
import com.capstone.petverse.data.response.LoginResponse
import com.capstone.petverse.data.response.SignupResponse
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService
) {
    suspend fun registerUser(name: String, username: String, email: String, password: String): Response<SignupResponse> {
        return apiService.registerUser(name, username, email, password)
    }

    suspend fun loginUser(email: String, password: String): Response<LoginResponse> {
        return apiService.loginUser(email, password)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService).also { instance = it }
            }
    }
}