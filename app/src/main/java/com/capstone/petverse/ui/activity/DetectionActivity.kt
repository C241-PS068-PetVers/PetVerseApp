package com.capstone.petverse.ui.activity

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.capstone.petverse.R
import com.capstone.petverse.ui.viewmodel.ResultViewModel
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun DetectionScreen(navController: NavHostController? = null, resultViewModel: ResultViewModel) {
    val pickedImageUri by resultViewModel.pickedImageUri.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { resultViewModel.setPickedImageUri(it) }
    }

    val imageCaptureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            resultViewModel.pickedImageUri.value?.let { uri ->
                val file = File(uri.path ?: "")
                scope.launch {
                    val detected = resultViewModel.detectImage(context, uri)
                    if (detected) {
                        navController?.navigate("result")
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImagePlaceholderComponent(
                imageUri = pickedImageUri,
                onDetectClick = {
                    pickedImageUri?.let { uri ->
                        scope.launch {
                            val detected = resultViewModel.detectImage(context, uri)
                            if (detected) {
                                navController?.navigate("result")
                            }
                        }
                    }
                },
                onCaptureClick = {
                    val uri = resultViewModel.createImageUri(context)
                    resultViewModel.setPickedImageUri(uri)
                    if (uri != null) {
                        imageCaptureLauncher.launch(uri)
                    }
                },
                onPickClick = { imagePickerLauncher.launch("image/*") }
            )
        }
    }
}


@Composable
fun ImagePlaceholderComponent(
    imageUri: Uri?,
    onDetectClick: () -> Unit,
    onCaptureClick: () -> Unit,
    onPickClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onDetectClick, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF28705))) {
            Text(text = stringResource(id = R.string.detect))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onCaptureClick) {
                Text(text = stringResource(id = R.string.capture_image))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = onPickClick) {
                Text(text = stringResource(id = R.string.pick_image))
            }
        }
    }
}
