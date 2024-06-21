// SignupViewModel.kt
package com.capstone.petverse.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.petverse.data.repository.UserRepository
import com.capstone.petverse.ui.activity.LoginActivity
import kotlinx.coroutines.launch

class SignupViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _isPasswordVisible = MutableLiveData<Boolean>()
    val isPasswordVisible: LiveData<Boolean> get() = _isPasswordVisible

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    init {
        _isPasswordVisible.value = false
    }

    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun clearName() {
        _name.value = ""
    }

    fun clearUsername() {
        _username.value = ""
    }

    fun clearEmail() {
        _email.value = ""
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = _isPasswordVisible.value != true
    }

    fun onSignupClicked(context: Context) {
        val nameValue = name.value.orEmpty()
        val usernameValue = username.value.orEmpty()
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()

        Log.e("SignupInput", "Email: $emailValue, Password: $passwordValue, Name: $nameValue, Username: $usernameValue")

        if (emailValue.isNotEmpty() && passwordValue.isNotEmpty()) {
            if (!isPasswordValid(passwordValue)) {
                _error.value = "Password must be at least 7 characters long and contain at least one digit"
                return
            }
            if (!isUsernameValid(usernameValue)) {
                _error.value = "Username cannot contain spaces"
                return
            }
            _error.value = null

            viewModelScope.launch {
                try {
                    val response = userRepository.registerUser(nameValue, usernameValue, emailValue, passwordValue)
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                        navigateToLogin(context)
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("SignupError", "Error: $errorBody")
                        _error.value = "Sign Up Failed: ${response.message()} - $errorBody"
                    }
                } catch (e: Exception) {
                    Log.e("SignupError", "Exception: ${e.message}")
                    _error.value = "Sign Up Failed: ${e.message}"
                }
            }
        } else {
            _error.value = "Email and Password cannot be empty"
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 7 && password.any { it.isDigit() }
    }

    private fun isUsernameValid(username: String): Boolean {
        return !username.contains(" ")
    }

    fun navigateToLogin(context: Context) {
        Log.d("NavigateToLogin", "Navigating to LoginActivity")
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }
}
