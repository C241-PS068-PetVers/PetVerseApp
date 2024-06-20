package com.capstone.petverse.data.repository

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.capstone.petverse.data.model.UserModel
import com.capstone.petverse.data.pref.UserPreference
import com.capstone.petverse.data.remote.ApiService
import com.capstone.petverse.data.response.LikeHistoryResponse
import com.capstone.petverse.data.response.LoginResponse
import com.capstone.petverse.data.response.PostResponse
import com.capstone.petverse.data.response.SignupResponse
import com.capstone.petverse.data.response.UserProfileResponse
import com.capstone.petverse.ui.model.PostUser
import com.google.gson.Gson
import java.util.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    suspend fun createPost(description: String, category: String, bitmap: Bitmap?, token: String, phoneNumber: String? = null): Response<PostResponse> {
        val parts = mutableMapOf<String, RequestBody>()
        parts["description"] = description.toRequestBody("text/plain".toMediaTypeOrNull())
        parts["category"] = category.toRequestBody("text/plain".toMediaTypeOrNull())
        phoneNumber?.let {
            parts["phoneNumber"] = it.toRequestBody("text/plain".toMediaTypeOrNull())
        }

        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val requestBody = stream.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("image", "image.jpg", requestBody)

        return apiService.createPost(parts, filePart, "Bearer $token")
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

    fun parseErrorResponse(response: Response<*>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            errorResponse.message ?: "Unknown error occurred"
        } catch (e: Exception) {
            "Unknown error occurred"
        }
    }

    suspend fun getUserProfile(token: String): Response<UserProfileResponse> {
        return apiService.getUserProfile(token)
    }

//    suspend fun getLikedPosts(token: String): Response<LikeHistoryResponse> {
//        return apiService.getLikedPosts("Bearer $token")
//    }
    suspend fun getLikedPosts(token: String): Response<List<PostUser>> {
        val response = apiService.getLikedPosts("Bearer $token")
        return if (response.isSuccessful) {
            val likedPosts = response.body()?.posts?.map { postItemLike ->
                PostUser(
                    id = postItemLike?.id ?: "",
                    imageUrl = postItemLike?.imageUrl ?: "",
                    description = postItemLike?.description ?: "",
                    authorName = postItemLike?.authorName ?: "",
                    authorProfilePicture = postItemLike?.authorProfilePicture,
                    likes = postItemLike?.likes?.filterNotNull() ?: emptyList(),
                    category = postItemLike?.category ?: "",
                    phoneNumber = postItemLike?.phoneNumber
                )
            } ?: emptyList()
            Response.success(likedPosts)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }



    suspend fun updateUserProfile(name: String, username: String, bitmap: Bitmap?, token: String): Response<UserProfileResponse> {
        val namePart = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val usernamePart = username.toRequestBody("text/plain".toMediaTypeOrNull())

        val profilePicturePart = bitmap?.let {
            val stream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            val requestBody = stream.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("profilePicture", "image.jpg", requestBody)
        }

        return apiService.updateUserProfile("Bearer $token", namePart, usernamePart, profilePicturePart)
    }

    suspend fun getPostsByCategory(token: String, category: String): Response<List<PostResponse>> {
        return apiService.getPostsByCategory(token, category)
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
