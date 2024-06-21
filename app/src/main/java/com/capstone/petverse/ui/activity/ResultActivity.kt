package com.capstone.petverse.ui.activity

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.capstone.petverse.R
import com.capstone.petverse.ui.viewmodel.ResultViewModel
import kotlinx.coroutines.launch

@Composable
fun ResultActivity(navController: NavHostController? = null, viewModel: ResultViewModel = viewModel()) {
    val pickedImageUri by viewModel.pickedImageUri.collectAsState()
    val analysisResult by viewModel.analysisResult.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Log.d("ResultActivity", "Picked Image URI: $pickedImageUri")
    Log.d("ResultActivity", "Analysis Result: $analysisResult")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImagePlaceholderComponent(imageUri = pickedImageUri)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = analysisResult,
                    modifier = Modifier.padding(16.dp)
                )
                if (error.isNotEmpty()) {
                    Text(
                        text = error,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(
                        onClick = {
                            // Retry logic here
                            pickedImageUri?.let { uri ->
                                viewModel.resetError()
                                coroutineScope.launch {
                                    viewModel.detectImage(navController?.context!!, uri)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(text = stringResource(R.string.retry))
                    }
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController?.navigateUp() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(text = stringResource(R.string.back_to_home))
                    }
                }
            }
        }
    }
}

@Composable
fun ImagePlaceholderComponent(imageUri: Uri?) {
    if (imageUri != null) {
        Image(
            painter = rememberImagePainter(data = imageUri),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.wf_image_placeholder),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
        )
    }
}
