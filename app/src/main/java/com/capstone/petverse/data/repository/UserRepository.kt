package com.capstone.petverse.data.repository

import android.graphics.Bitmap
import com.capstone.petverse.data.model.UserModel
import com.capstone.petverse.data.pref.UserPreference
import com.capstone.petverse.data.remote.ApiService
import com.capstone.petverse.data.response.LoginResponse
import com.capstone.petverse.data.response.PostResponse
import com.capstone.petverse.data.response.SignupResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.ByteArrayOutputStream

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    suspend fun registerUser(name: String, username: String, email: String, password: String): Response<SignupResponse> {
        return apiService.registerUser(name, username, email, password)
    }

    suspend fun loginUser(email: String, password: String): Response<LoginResponse> {
        val response = apiService.loginUser(email, password)
        if (response.isSuccessful && response.body()?.success == true){
            val user = response.body()?.user?.let { user ->
                UserModel(user.email ?: "", response.body()?.token ?: "", response.body()?.success ?: false)
            }
            user?.let {
                saveSession(it)
            }
        }
        return response
    }

    suspend fun createPost(description: String, category: String, bitmap: Bitmap?, token: String): Response<PostResponse> {
        val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())

        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("image", "image.jpg", requestBody)

        return apiService.createPost(descriptionBody, categoryBody, filePart, "Bearer $token")
    }

    suspend fun getPosts(token: String): Response<List<PostResponse>> {
        return apiService.getPosts("Bearer $token")
    }

    suspend fun saveSession(user: UserModel) {
        val currentTime = System.currentTimeMillis()
        val expiryTime = currentTime + 3600000 // 1 hour in milliseconds
        userPreference.saveSession(user, expiryTime)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun likePost(token: String, postId: String): Response<PostResponse> {
        val postIdMap = mapOf("id" to postId)
        return apiService.likePost("Bearer $token", postIdMap)
    }

    suspend fun unlikePost(token: String, postId: String): Response<PostResponse> {
        val postIdMap = mapOf("id" to postId)
        return apiService.unlikePost("Bearer $token", postIdMap)
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference).also { instance = it }
            }
    }
}
