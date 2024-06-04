package com.capstone.petverse.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.petverse.data.model.User
import com.capstone.petverse.data.remote.apiService
import com.capstone.petverse.ui.activity.LoginActivity
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()


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

    private val _fullName = MutableLiveData<String>()
    val fullName: LiveData<String> get() = _fullName

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
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()
        val nameValue = name.value.orEmpty()
        val usernameValue = username.value.orEmpty()

        if (emailValue.isNotEmpty() && passwordValue.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(emailValue, passwordValue)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show()

                        saveUserInfoToFirestore(nameValue, usernameValue, emailValue, passwordValue)

                        _name.value = nameValue
                        _username.value = usernameValue

                        navigateToLogin(context)
                    } else {
                        Toast.makeText(context, "Sign Up Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(context, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

//    pakai api localhost
//fun onSignupClicked(context: Context) {
//    val emailValue = email.value.orEmpty()
//    val passwordValue = password.value.orEmpty()
//    val nameValue = name.value.orEmpty()
//    val usernameValue = username.value.orEmpty()
//
//    // Check if all fields are filled
//    if (emailValue.isNotEmpty() && passwordValue.isNotEmpty() && nameValue.isNotEmpty() && usernameValue.isNotEmpty()) {
//        // Validate email format
//        if (!emailValue.contains("@")) {
//            Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val user = User(name = nameValue, username = usernameValue, email = emailValue, password = passwordValue)
//
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response = apiService.signup(user)
//                withContext(Dispatchers.Main) {
//                    if (response.isSuccessful && response.body()?.success == true) {
//                        Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show()
//
//                        saveUserInfoToFirebase(nameValue, usernameValue, emailValue, passwordValue)
//
//                        _name.value = nameValue
//                        _username.value = usernameValue
//
//                        Log.d("SignUp", "Calling navigateToLogin")
//                        navigateToLogin(context)
//                    } else {
//                        val errorBody = response.errorBody()?.string()
//                        Log.e("Sign Up Failed", "Response: $errorBody")
//
//                        Toast.makeText(context, "Sign Up Failed: ${response.body()?.message ?: "Unknown error"}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(context, "Sign Up Failed: ${e.message}", Toast.LENGTH_SHORT).show()
//                    Log.e("Sign Up Failed", "Exception: ${e.message}")
//                }
//            }
//        }
//    } else {
//        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
//    }
//}








//

    fun saveUserInfoToFirestore(name: String, username: String, email: String, password: String) {
        val currentUser = auth.currentUser
        val userId = currentUser?.uid.orEmpty()

        val userInfo = hashMapOf(
            "name" to name,
            "username" to username,
            "email" to email,
            "password" to password
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                db.collection("users").document(userId).set(userInfo)
                withContext(Dispatchers.Main) {
                    Log.d("SaveUserInfo", "User info saved to Firestore di GCP")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("SaveUserInfo", "Failed to save user info to Firestore di GCP: ${e.message}")
                }
            }
        }
    }


//    pakai api localhost

//    fun saveUserInfoToFirebase(name: String, username: String, email: String, password: String) {
//        val currentUser = auth.currentUser
//        val userId = currentUser?.uid.orEmpty()
//
//        val userInfo = hashMapOf(
//            "name" to name,
//            "username" to username,
//            "email" to email,
//            "password" to password
//        )
//
//        db.collection("users").document(userId).set(userInfo)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("SaveUserInfo", "User info saved to Firestore")
//                } else {
//                    Log.e("SaveUserInfo", "Failed to save user info: ${task.exception?.message}")
//                }
//            }
//    }


//    fun fetchUserInfo() {
//        val userId = auth.currentUser?.uid.orEmpty()
//        if (userId.isNotEmpty()) {
//            db.collection("users").document(userId).get()
//                .addOnSuccessListener { document ->
//                    if (document != null) {
//                        _name.value = document.getString("name") ?: "No Name"
//                        _username.value = document.getString("username") ?: "No Username"
//                        Log.d("FetchUserInfo", "User data: ${document.data}")
//                    } else {
//                        Log.d("FetchUserInfo", "No such document")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.d("FetchUserInfo", "get failed with ", exception)
//                }
//        } else {
//            Log.d("FetchUserInfo", "User ID is empty")
//        }
//    }

    fun navigateToLogin(context: Context) {
        Log.d("NavigateToLogin", "Navigating to LoginActivity")
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }
}
