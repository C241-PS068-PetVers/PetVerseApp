package com.capstone.petverse.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EditProfileViewModel : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username

    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun clearName() {
        _name.value = ""
    }

    fun clearUsername() {
        _username.value = ""
    }
}
