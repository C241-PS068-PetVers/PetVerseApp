package com.capstone.petverse.data.remote

import com.capstone.petverse.data.response.DetectionResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DetectionApiService {
    @Multipart
    @POST("/predict")
    suspend fun detectImage(
        @Part imagefile: MultipartBody.Part
    ): DetectionResponse?
}
