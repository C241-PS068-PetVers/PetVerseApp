package com.capstone.petverse.ui.viewmodel

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import com.capstone.petverse.data.remote.DetectionRetrofitClient
import com.capstone.petverse.data.response.DetectionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream

class ResultViewModel : ViewModel() {

    private val _pickedImageUri = MutableStateFlow<Uri?>(null)
    val pickedImageUri: StateFlow<Uri?> = _pickedImageUri

    private val _analysisResult = MutableStateFlow("")
    val analysisResult: StateFlow<String> = _analysisResult

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    fun setPickedImageUri(uri: Uri?) {
        Log.d("ResultViewModel", "setPickedImageUri: $uri")
        _pickedImageUri.value = uri
    }

    fun setAnalysisResult(result: String) {
        Log.d("ResultViewModel", "setAnalysisResult: $result")
        _analysisResult.value = result
    }

    fun resetError() {
        _error.value = ""
    }

    suspend fun detectImage(context: Context, uri: Uri): Boolean {
        return try {
            _loading.value = true
            _error.value = ""
            Log.d("ResultViewModel", "detectImage: Start detection")
            val file = getFileFromUri(context, uri)
            Log.d("ResultViewModel", "detectImage: File path - ${file.absolutePath}")
            val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("imagefile", file.name, requestFile)
            Log.d("ResultViewModel", "detectImage: Request body created - ${body.body.contentLength()} bytes")

            val response: DetectionResponse? = DetectionRetrofitClient.apiService.detectImage(body)
            response?.let {
                val resultText = "Analisis menunjukkan hewan ini dikategorikan sebagai ras ${it.result ?: "Tidak diketahui"} dengan tingkat accuracy ${it.percentage ?: 0}%"
                setAnalysisResult(resultText)
                Log.d("ResultViewModel", "detectImage: Detection successful - $resultText")
                true
            } ?: false
        } catch (e: HttpException) {
            _error.value = "Analisis gagal: ${e.response()?.errorBody()?.string()}"
            Log.e("ResultViewModel", "detectImage: Detection failed with HTTP ${e.code()}", e)
            false
        } catch (e: Exception) {
            _error.value = "Analisis gagal: ${e.message}"
            Log.e("ResultViewModel", "detectImage: Detection failed", e)
            false
        } finally {
            _loading.value = false
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload", ".jpg")
        inputStream.use { input ->
            FileOutputStream(tempFile).use { output ->
                input?.copyTo(output)
            }
        }
        return tempFile
    }

    fun createImageUri(context: Context): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "temp_image")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }
        return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }
}
