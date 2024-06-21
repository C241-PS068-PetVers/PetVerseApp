package com.capstone.petverse.data.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
	@SerializedName("id")
	val id: String? = null,

	@SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@SerializedName("createdAt")
	val createdAt: CreatedAt? = null,

	@SerializedName("username")
	val username: String? = null,

	@SerializedName("email")
	val email: String? = null,

	@SerializedName("imageUrl")
	val imageUrl: String? = null,

	@SerializedName("description")
	val description: String? = null,

	@SerializedName("category")
	val category: String? = null,

	@SerializedName("likes")
	val likes: List<String?>? = null,

	@SerializedName("profilePicture")
	val profilePicture: String? = null
)

data class CreatedAt(
	@SerializedName("_seconds")
	val seconds: Int? = null,

	@SerializedName("_nanoseconds")
	val nanoseconds: Int? = null
)
