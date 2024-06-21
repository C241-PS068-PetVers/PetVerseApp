package com.capstone.petverse.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.capstone.petverse.data.model.UserModel
import com.capstone.petverse.data.repository.UserRepository
import com.capstone.petverse.ui.model.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class EditProfileViewModel(application: Application, private val userRepository: UserRepository) : AndroidViewModel(application) {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap: StateFlow<Bitmap?> = _bitmap

    private val _userSession = MutableStateFlow<UserModel?>(null)
    val userSession: StateFlow<UserModel?> = _userSession

    init {
        viewModelScope.launch {
            userRepository.getSession().collect { session ->
                _userSession.value = session
            }
        }
    }

    fun clearName() {
        _name.value = ""
    }

    fun clearUsername() {
        _username.value = ""
    }

    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }



    fun setImageUri(uri: Uri?) {
        _imageUri.value = uri
        if (uri != null) {
            viewModelScope.launch {
                _bitmap.value = loadAndCompressImage(uri)
            }
        }
    }

    private suspend fun loadAndCompressImage(uri: Uri): Bitmap? {
        val originalBitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(getApplication<Application>().contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(getApplication<Application>().contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
        return compressBitmap(originalBitmap)
    }

    private fun compressBitmap(bitmap: Bitmap): Bitmap {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val byteArray = stream.toByteArray()
        return Bitmap.createScaledBitmap(
            bitmap,
            bitmap.width / 2,
            bitmap.height / 2,
            true
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateProfile(navController: NavController) {
        viewModelScope.launch {
            if (userSession.value == null) {
                return@launch
            }

            val user = userSession.value!!
            val token = user.token

            Log.d("UpdateProfile", "Token: $token")
            Log.d("UpdateProfile", "Name: ${name.value}")
            Log.d("UpdateProfile", "Username: ${username.value}")

            val response = userRepository.updateUserProfile(
                name = name.value,
                username = username.value,
                bitmap = bitmap.value,
                token = token
            )

            if (response.isSuccessful) {
                navController.navigate(Screen.Profile.route) {
                    popUpTo(Screen.EditProfile.route) { inclusive = true }
                }
            } else {
                // Handle error
                Log.e("UpdateProfile", "Failed to update profile: ${response.message()}")
            }
        }
    }
}
