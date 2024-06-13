package com.capstone.petverse.ui.activity

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.petverse.R
import com.capstone.petverse.ui.components.CardPost
import com.capstone.petverse.ui.viewmodel.UploadPostViewModel
import com.capstone.petverse.ui.viewmodel.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: UploadPostViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current.applicationContext as Application, LocalContext.current))
) {
    val posts by viewModel.posts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = colorResource(id = R.color.colorPrimary)

                )
            }
            if (posts.isEmpty() && !isLoading) {
                Text(
                    text = "No posts available",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(items = posts, key = { item -> item.id }) { item ->
                        CardPost(item, viewModel, modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }
    }
}
