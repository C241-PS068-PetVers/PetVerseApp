package com.capstone.petverse.ui.model

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Upload : Screen("upload")
    object Detection : Screen("detection")
    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")
    object PostList : Screen("postList/{postId}") {
        fun createRoute(postId: String) = "postList/$postId"
    }
}