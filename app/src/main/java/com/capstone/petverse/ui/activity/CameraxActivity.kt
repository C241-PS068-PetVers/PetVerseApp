package com.capstone.petverse.ui.activity

import android.content.Context
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.capstone.petverse.ui.viewmodel.CameraxViewModel

@Composable
fun CameraPreviewScreen(navController: NavHostController) {
    val viewModel: CameraxViewModel = viewModel()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    val imageCapture = viewModel.imageCapture.value

    LaunchedEffect(key1 = Unit) {
        imageCapture?.let {
            viewModel.initCamera(context, lifecycleOwner, previewView, CameraSelector.LENS_FACING_BACK)
        }
    }

    // Handle back button press
    BackHandler {
        navController.popBackStack()
    }

    // Launcher for picking images from the gallery
    val imagePickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.setPickedImageUri(it) }
    }

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        IconButton(onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = Color.White)
        }

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                imageCapture?.let {
                    viewModel.captureImage(it, context)
                }
            }) {
                Text(text = "Capture Image")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                imagePickerLauncher.launch("image/*")
            }) {
                Text(text = "Pick Image")
            }
        }
    }
}
