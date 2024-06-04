package com.capstone.petverse.data.model


data class LoginUser(
    val email: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val message: String
)