package com.capstone.petverse.data.remote

import com.capstone.petverse.data.response.LoginResponse
import com.capstone.petverse.data.response.PostResponse
import com.capstone.petverse.data.response.SignupResponse
import com.capstone.petverse.data.response.UserProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

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
        @PartMap parts: Map<String, @JvmSuppressWildcards RequestBody>,
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

    @GET("/api/user/profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): Response<UserProfileResponse>
}
