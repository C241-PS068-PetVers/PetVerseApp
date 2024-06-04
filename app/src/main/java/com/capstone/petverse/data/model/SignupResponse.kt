package com.capstone.petverse.data.model

data class User(
    val name: String,
    val username: String,
    val email: String,
    val password: String
)

data class SignupResponse(
    val success: Boolean,
    val message: String
)


