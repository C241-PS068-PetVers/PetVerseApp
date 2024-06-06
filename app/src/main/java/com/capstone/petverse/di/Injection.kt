package com.capstone.petverse.di

import com.capstone.petverse.data.remote.RetrofitClient
import com.capstone.petverse.data.repository.UserRepository

object Injection {
    fun provideUserRepository(): UserRepository {
        val apiService = RetrofitClient.apiService
        return UserRepository.getInstance(apiService)
    }
}
