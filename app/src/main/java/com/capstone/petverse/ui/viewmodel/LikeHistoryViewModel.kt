package com.capstone.petverse.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.petverse.data.model.UserModel
import com.capstone.petverse.data.repository.UserRepository
import com.capstone.petverse.ui.model.PostUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LikeHistoryViewModel(application: Application, private val userRepository: UserRepository) : AndroidViewModel(application) {
    private val _likedPosts = MutableStateFlow<List<PostUser>>(emptyList())
    val likedPosts: StateFlow<List<PostUser>> = _likedPosts

    val userSession = MutableStateFlow<UserModel?>(null)

    init {
        fetchLikedPosts()
    }

    private fun fetchLikedPosts() {
        viewModelScope.launch {
            val user = userRepository.getSession().first()
            userSession.value = user
            if (user.isLogin) {
                val response = userRepository.getLikedPosts(user.token)
                if (response.isSuccessful) {
                    _likedPosts.value = response.body()?.filterNotNull() ?: emptyList()
                }
            }
        }
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
                }
            }
        }
    }

    private fun updateLocalPostLikeStatus(postId: String, email: String, isLiked: Boolean) {
        _likedPosts.value = _likedPosts.value.map { post ->
            if (post.id == postId) {
                val updatedLikes = if (isLiked) {
                    post.likes + email
                } else {
                    post.likes - email
                }
                post.copy(likes = updatedLikes)
            } else {
                post
            }
        }
    }
}
