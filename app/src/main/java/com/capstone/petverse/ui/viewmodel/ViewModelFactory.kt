package com.capstone.petverse.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.petverse.data.repository.UserRepository
import com.capstone.petverse.di.Injection

class ViewModelFactory private constructor(
    private val application: Application,
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> SignupViewModel(userRepository) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(userRepository) as T
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(userRepository) as T
            modelClass.isAssignableFrom(UploadPostViewModel::class.java) -> UploadPostViewModel(application, userRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(application: Application, context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(application, Injection.provideUserRepository(context)).also { instance = it }
            }
    }
}
