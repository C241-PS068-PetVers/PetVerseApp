package com.capstone.petverse.di

import android.content.Context
import com.capstone.petverse.data.pref.UserPreference
import com.capstone.petverse.data.pref.dataStore
import com.capstone.petverse.data.remote.RetrofitClient
import com.capstone.petverse.data.repository.UserRepository

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val apiService = RetrofitClient.apiService
        val userPreference = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(apiService, userPreference)
    }

    
}
