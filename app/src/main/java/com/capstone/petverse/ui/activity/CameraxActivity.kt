package com.capstone.petverse.ui.activity

import android.content.Context
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.capstone.petverse.R
import com.capstone.petverse.ui.viewmodel.CameraxViewModel

@Composable
fun CameraPreviewScreen(navController: NavHostController) {
    val viewModel: CameraxViewModel = viewModel()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    val imageCapture = viewModel.imageCapture.value
    val pickedImageUri by viewModel.pickedImageUri.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.initCamera(context, lifecycleOwner, previewView, CameraSelector.LENS_FACING_BACK)
    }

    // Handle back button press
    BackHandler {
        navController.popBackStack()
    }

    // Launcher for picking images from the gallery
    val imagePickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.setPickedImageUri(it) }
    }

    // Navigate to DetectionActivity if an image is captured or picked
    LaunchedEffect(pickedImageUri) {
        pickedImageUri?.let {
            navController.navigate("detection")
        }
    }

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        IconButton(onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(id = R.string.close), tint = Color.White)
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
                Text(text = stringResource(id = R.string.capture_image))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                imagePickerLauncher.launch("image/*")
            }) {
                Text(text = stringResource(id = R.string.pick_image))
            }
        }
    }
}
