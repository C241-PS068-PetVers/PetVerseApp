package com.capstone.petverse.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.petverse.data.repository.UserRepository
import com.capstone.petverse.di.Injection

class ViewModelFactory private constructor(
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val userRepository = Injection.provideUserRepository(application)
        return when {
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> SignupViewModel(userRepository) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(userRepository) as T
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(userRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(userRepository) as T
            modelClass.isAssignableFrom(UploadPostViewModel::class.java) -> UploadPostViewModel(application, userRepository) as T
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> EditProfileViewModel(application, userRepository) as T
            modelClass.isAssignableFrom(LikeHistoryViewModel::class.java) -> LikeHistoryViewModel(application, userRepository) as T
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(application).also { instance = it }
            }
    }
}
