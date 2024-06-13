package com.capstone.petverse.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.capstone.petverse.data.repository.UserRepository

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getSession() = userRepository.getSession()
}
