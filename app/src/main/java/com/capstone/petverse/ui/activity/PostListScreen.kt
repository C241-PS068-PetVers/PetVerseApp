package com.capstone.petverse.ui.activity

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.capstone.petverse.ui.components.ProfileCardPost
import com.capstone.petverse.ui.viewmodel.ProfileViewModel

@Composable
fun PostListScreen(
    navController: NavController,
    category: String,
    startPostId: String,
    profileViewModel: ProfileViewModel,
    token: String // Add token parameter
) {
    profileViewModel.fetchPostsByCategory(token, category) // Pass token when fetching posts

    val posts by profileViewModel.posts.collectAsState()
    val startIndex = posts.indexOfFirst { it.id == startPostId }

    // Log the number of posts fetched
    Log.d("PostListScreen", "Number of posts: ${posts.size}")

    Scaffold(
        topBar = {
            ProfileTopBar()
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(posts.size) { index ->
                val postIndex = (startIndex + index) % posts.size
                ProfileCardPost(
                    post = posts[postIndex],
                    viewModel = profileViewModel,
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}
