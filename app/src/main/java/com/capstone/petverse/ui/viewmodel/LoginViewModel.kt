package com.capstone.petverse.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.petverse.MainActivity
import com.capstone.petverse.data.model.LoginUser
import com.capstone.petverse.data.remote.apiService
import com.capstone.petverse.ui.activity.SignupActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        navigateToMain(context)
                    } else {
                        Toast.makeText(context, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(context, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

//    pakai api localhost
//    fun login(context: Context) {
//        val emailValue = _loginFormState.value?.email.orEmpty()
//        val passwordValue = _loginFormState.value?.password.orEmpty()
//
//        // Check if email and password are not empty
//        if (emailValue.isNotEmpty() && passwordValue.isNotEmpty()) {
//            // Validate email format
//            if (!emailValue.contains("@")) {
//                Toast.makeText(context, "Invalid email address. Please include '@' in your email.", Toast.LENGTH_SHORT).show()
//                return
//            }
//
//            val user = User(email = emailValue, password = passwordValue)
//
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    val response = apiService.login(user)
//                    withContext(Dispatchers.Main) {
//                        if (response.isSuccessful && response.body()?.success == true) {
//                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
//
//                            // Check if response body is not null
//                            response.body()?.user?.let { user ->
//                                saveUserInfoToFirebase(user)
//                            }
//
//                            navigateToMain(context)
//                        } else {
//                            val errorBody = response.errorBody()?.string()
//                            Log.e("Login Failed", "Response: $errorBody")
//
//                            // Handle null response body
//                            if (response.body() == null) {
//                                Toast.makeText(context, "Login Failed: Null response from server", Toast.LENGTH_SHORT).show()
//                            } else {
//                                Toast.makeText(context, "Login Failed: ${response.body()?.message ?: "Unknown error"}", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    }
//                } catch (e: Exception) {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(context, "Login Failed: ${e.message}", Toast.LENGTH_SHORT).show()
//                        Log.e("Login Failed", "Exception: ${e.message}")
//                    }
//                }
//            }
//        } else {
//            Toast.makeText(context, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
//        }
//    }



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
