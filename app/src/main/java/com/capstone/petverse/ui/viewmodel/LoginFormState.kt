package com.capstone.petverse.ui.viewmodel

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false
)
