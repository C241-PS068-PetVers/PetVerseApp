package com.capstone.petverse.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.capstone.petverse.data.model.UserModel
import com.capstone.petverse.data.repository.UserRepository
import com.capstone.petverse.data.response.PostResponse
import com.capstone.petverse.ui.model.PostUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class UploadPostViewModel(application: Application, private val userRepository: UserRepository) : AndroidViewModel(application) {
    val imageUri = mutableStateOf<Uri?>(null)
    val bitmap = mutableStateOf<Bitmap?>(null)
    val selectedCategory = mutableStateOf("")
    val description = mutableStateOf("")
    val userSession = mutableStateOf<UserModel?>(null)
    private val _posts = MutableStateFlow<List<PostUser>>(emptyList())
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    val posts: StateFlow<List<PostUser>> get() = _posts

    init {
        viewModelScope.launch {
            userRepository.getSession().collect { session ->
                userSession.value = session
                fetchPosts()  // Fetch posts when the session is loaded
            }
        }
    }

    fun setImageUri(uri: Uri?) {
        imageUri.value = uri
        if (uri != null) {
            viewModelScope.launch {
                bitmap.value = loadAndCompressImage(uri)
            }
        }
    }

    fun setSelectedCategory(category: String) {
        selectedCategory.value = category
    }

    fun setDescription(desc: String) {
        description.value = desc
    }

    fun onSubmitPost(navController: NavController) {
        viewModelScope.launch {
            userSession.value?.let { user ->
                val token = user.token
                val postResponse = userRepository.createPost(
                    description.value,
                    selectedCategory.value,
                    bitmap.value,
                    token
                )
                if (postResponse.isSuccessful) {
                    fetchPosts()
                    navController.popBackStack()  // Navigate back on successful post
                } else {
                    // Handle error
                }
            }
        }
    }

    fun fetchPosts() {
        viewModelScope.launch {
            userSession.value?.let { user ->
                _isLoading.value = true
                val token = user.token
                val response = userRepository.getPosts(token)
                if (response.isSuccessful) {
                    val postsResponse = response.body()
                    Log.d("UploadPostViewModel", "API Response: $postsResponse") // Log the API response
                    _posts.value = postsResponse?.map { postResponse ->
                        mapPostToPostUser(postResponse)
                    } ?: emptyList()
                } else {
                    Log.e("UploadPostViewModel", "Error fetching posts: ${response.errorBody()?.string()}")
                }
                _isLoading.value = false
            }
        }
    }


    private fun mapPostToPostUser(post: PostResponse): PostUser {
        Log.d("UploadPostViewModel", "Mapping post: $post")
        return PostUser(
            id = post.id ?: post.hashCode().toString(),
            authorName = post.authorName ?: "Unknown",
            imageUrl = post.imageUrl ?: "",
            description = post.description ?: "",
            category = post.category ?: "",
            likes = post.likes?.filterNotNull() ?: emptyList(),
            commentsCount = 0 // Default value
        )
    }


    fun likePost(postId: String) {
        viewModelScope.launch {
            userSession.value?.let { user ->
                val token = user.token
                val response = userRepository.likePost(token, postId)
                if (response.isSuccessful) {
                    updateLocalPostLikeStatus(postId, user.email, true)
                } else {
                    // Handle error
                    val errorBody = response.errorBody()?.string()
                    Log.e("UploadPostViewModel", "Failed to like post: $errorBody")
                }
            }
        }
    }

    fun unlikePost(postId: String) {
        viewModelScope.launch {
            userSession.value?.let { user ->
                val token = user.token
                val response = userRepository.unlikePost(token, postId)
                if (response.isSuccessful) {
                    updateLocalPostLikeStatus(postId, user.email, false)
                } else {
                    // Handle error
                    val errorBody = response.errorBody()?.string()
                    Log.e("UploadPostViewModel", "Failed to unlike post: $errorBody")
                }
            }
        }
    }

    private fun updateLocalPostLikeStatus(postId: String, userEmail: String, isLiked: Boolean) {
        _posts.value = _posts.value.map { post ->
            if (post.id == postId) {
                val updatedLikes = if (isLiked) {
                    post.likes + userEmail
                } else {
                    post.likes - userEmail
                }
                post.copy(likes = updatedLikes)
            } else {
                post
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
}
