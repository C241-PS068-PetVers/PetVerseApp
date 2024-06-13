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

    fun onEmailChange(email: String) {
        val currentState = _loginFormState.value ?: LoginFormState()
        _loginFormState.value = currentState.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        val currentState = _loginFormState.value ?: LoginFormState()
        _loginFormState.value = currentState.copy(password = password)
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
                    if (response.isSuccessful && response.body()?.success == true) {
                        val user = response.body()?.user
                        val token = response.body()?.token
                        if (user != null && token != null) {
                            val userModel = UserModel(user.email ?: "", token, true)
                            userRepository.saveSession(userModel)
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                            navigateToMain(context)
                        }
                    } else {
                        Toast.makeText(context, "Login Failed: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                        Log.e("LoginError", "Error: ${response.body()?.message}")
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Login Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("LoginError", "Exception: ${e.message}")
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            Toast.makeText(context, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
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
