package com.capstone.petverse.ui.viewmodel

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CameraxViewModel : ViewModel() {

    private val _imageCapture = mutableStateOf<ImageCapture?>(null)
    val imageCapture: State<ImageCapture?> = _imageCapture

    private val _pickedImageUri = mutableStateOf<Uri?>(null)
    val pickedImageUri: State<Uri?> = _pickedImageUri

    init {
        initializeImageCapture()
    }

    private fun initializeImageCapture() {
        _imageCapture.value = ImageCapture.Builder().build()
    }

    fun captureImage(imageCapture: ImageCapture, context: Context) {
        val name = "CameraxImage.jpeg"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.d("CameraPreview", "Image saved successfully")
                    // Update state or handle success
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("CameraPreview", "Image capture failed", exception)
                    // Handle error
                }
            })
    }

    fun initCamera(context: Context, lifecycleOwner: LifecycleOwner, previewView: PreviewView, lensFacing: Int) {
        viewModelScope.launch {
            try {
                val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
                val preview = Preview.Builder().build()
                val imageCapture = ImageCapture.Builder().build()

                val cameraProvider = context.getCameraProvider()
                cameraProvider.unbindAll()

                preview.setSurfaceProvider(previewView.surfaceProvider)
                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.e("CameraPreview", "Camera binding failed", e)
            }
        }
    }

    fun setPickedImageUri(uri: Uri?) {
        _pickedImageUri.value = uri
    }

    private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
        suspendCoroutine { continuation ->
            val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
            cameraProviderFuture.addListener({
                continuation.resume(cameraProviderFuture.get())
            }, ContextCompat.getMainExecutor(this))
        }
}
