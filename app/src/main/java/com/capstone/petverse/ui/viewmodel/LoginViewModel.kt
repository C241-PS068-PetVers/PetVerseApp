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
import com.capstone.petverse.ui.activity.SignupActivity
import com.capstone.petverse.data.repository.UserRepository
import com.capstone.petverse.di.Injection
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val userRepository: UserRepository = Injection.provideUserRepository()

    private val _loginFormState = MutableLiveData(LoginFormState())
    val loginFormState: LiveData<LoginFormState> = _loginFormState

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
            viewModelScope.launch {
                try {
                    val response = userRepository.loginUser(email, password)
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        navigateToMain(context)
                    } else {
                        Toast.makeText(context, "Login Failed: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                        Log.e("SignupError", "Error: ${response.body()?.message}")
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Login Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("Login Failed:", "Error: ${e.message}")
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

    fun navigateToResetPassword(context: Context) {
        val intent = Intent(context, SignupActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToSignup(context: Context) {
        val intent = Intent(context, SignupActivity::class.java)
        context.startActivity(intent)
    }
}
