package com.capstone.petverse.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.petverse.MainActivity
import com.capstone.petverse.data.model.UserModel
import com.capstone.petverse.data.repository.UserRepository
import com.capstone.petverse.ui.activity.SignupActivity
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loginFormState = MutableLiveData(LoginFormState())
    val loginFormState: LiveData<LoginFormState> = _loginFormState

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _emailError = MutableLiveData<String?>(null)
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>(null)
    val passwordError: LiveData<String?> = _passwordError

    fun onEmailChange(email: String) {
        _emailError.value = null  // Clear previous errors
        _loginFormState.value = _loginFormState.value?.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _passwordError.value = null  // Clear previous errors
        _loginFormState.value = _loginFormState.value?.copy(password = password)
    }

    fun togglePasswordVisibility() {
        val currentState = _loginFormState.value ?: LoginFormState()
        _loginFormState.value = currentState.copy(isPasswordVisible = !currentState.isPasswordVisible)
    }

    fun login(context: Context) {
        val email = _loginFormState.value?.email.orEmpty()
        val password = _loginFormState.value?.password.orEmpty()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    val response = userRepository.loginUser(email, password)
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            if (body.success == true) {
                                val userEmail = body.user?.email ?: "Unknown Email" // Handling null
                                val userToken = body.token ?: "Unknown Token" // Handling null
                                userRepository.saveSession(UserModel(userEmail, userToken, true))
                                navigateToMain(context)
                            } else {
                                handleLoginError(body.message)
                            }
                        }
                    } else {
                        val errorMessage = userRepository.parseErrorResponse(response)
                        handleLoginError(errorMessage)
                    }
                } catch (e: Exception) {
                    _passwordError.postValue("Login Failed: ${e.localizedMessage ?: "Unknown error"}")
                    Log.e("LoginError", "Exception: ${e.message}")
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            Toast.makeText(context, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleLoginError(errorMessage: String?) {
        if (errorMessage != null) {
            if (errorMessage.contains("user not found", ignoreCase = true)) {
                _emailError.postValue(errorMessage)
            } else if (errorMessage.contains("invalid password", ignoreCase = true)) {
                _passwordError.postValue(errorMessage)
            } else {
                _passwordError.postValue(errorMessage)
            }
        } else {
            _passwordError.postValue("Login failed: Unknown error occurred")
        }
    }

    fun navigateToMain(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToSignup(context: Context) {
        val intent = Intent(context, SignupActivity::class.java)
        context.startActivity(intent)
    }
}

