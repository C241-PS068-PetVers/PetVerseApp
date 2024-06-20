package com.capstone.petverse.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.petverse.data.repository.UserRepository
import com.capstone.petverse.data.response.PostResponse
import com.capstone.petverse.data.response.UserProfile
import com.capstone.petverse.ui.model.PostUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    private val _posts = MutableStateFlow<List<PostUser>>(emptyList())
    val posts: StateFlow<List<PostUser>> = _posts.asStateFlow()

    private var userToken: String? = null

    fun onTabSelected(index: Int) {
        _selectedTabIndex.value = index
        userToken?.let { token ->
            fetchPostsByCategory(if (index == 0) "post" else "adoption", token)
        }
    }

    fun fetchUserProfile(token: String) {
        userToken = token
        viewModelScope.launch {
            val response = userRepository.getUserProfile(token)
            if (response.isSuccessful) {
                _userProfile.value = response.body()?.user
                fetchPostsByCategory("post", token)
            }
        }
    }

    fun fetchPostsByCategory(category: String, token: String) {
        viewModelScope.launch {
            Log.d("ProfileViewModel", "Fetching posts for category: $category with token: $token")
            val response = userRepository.getPostsByCategory(token, category)
            if (response.isSuccessful) {
                _posts.value = response.body()?.filter { it.authorName == _userProfile.value?.username }
                    ?.map { mapPostResponseToPostUser(it) } ?: emptyList()
                // Log the number of posts fetched
                Log.d("ProfileViewModel", "Fetched ${_posts.value.size} posts")
            } else {
                Log.e("ProfileViewModel", "Failed to fetch posts: ${response.errorBody()?.string()}")
            }
        }
    }

    private fun mapPostResponseToPostUser(postResponse: PostResponse): PostUser {
        return PostUser(
            id = postResponse.id ?: "",
            authorName = postResponse.authorName ?: "",
            imageUrl = postResponse.imageUrl ?: "",
            description = postResponse.description ?: "",
            category = postResponse.category ?: "",
            likes = postResponse.likes?.filterNotNull() ?: emptyList(),
            commentsCount = 0,
            phoneNumber = postResponse.phoneNumber,
            authorProfilePicture = postResponse.authorProfilePicture
        )
    }

    fun likePost(postId: String) {
        userToken?.let { token ->
            viewModelScope.launch {
                val response = userRepository.likePost(token, postId)
                if (response.isSuccessful) {
                    updateLocalPostLikeStatus(postId, _userProfile.value?.username ?: "", true)
                }
            }
        }
    }

    fun unlikePost(postId: String) {
        userToken?.let { token ->
            viewModelScope.launch {
                val response = userRepository.unlikePost(token, postId)
                if (response.isSuccessful) {
                    updateLocalPostLikeStatus(postId, _userProfile.value?.username ?: "", false)
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
}
