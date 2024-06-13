package com.capstone.petverse.data.remote

import com.capstone.petverse.data.response.LoginResponse
import com.capstone.petverse.data.response.PostResponse
import com.capstone.petverse.data.response.SignupResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @FormUrlEncoded
    @POST("/api/auth/register")
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<SignupResponse>

    @FormUrlEncoded
    @POST("/api/auth/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @Multipart
    @POST("/api/posts/upload")
    suspend fun createPost(
        @Part("description") description: RequestBody,
        @Part("category") category: RequestBody,
        @Part image: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Response<PostResponse>

    @GET("api/posts/getPost?category=")
    suspend fun getPosts(
        @Header("Authorization") token: String
    ): Response<List<PostResponse>>

    @POST("/api/posts/likePost")
    suspend fun likePost(
        @Header("Authorization") token: String,
        @Body postId: Map<String, String>
    ): Response<PostResponse>

    @POST("/api/posts/unlikePost")
    suspend fun unlikePost(
        @Header("Authorization") token: String,
        @Body postId: Map<String, String>
    ): Response<PostResponse>

}
