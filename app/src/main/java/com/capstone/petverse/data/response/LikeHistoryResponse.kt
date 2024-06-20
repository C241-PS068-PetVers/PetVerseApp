package com.capstone.petverse.data.response

import com.google.gson.annotations.SerializedName

data class LikeHistoryResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("posts")
	val posts: List<PostsItemLike?>? = null
)

data class UpdatedAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)

data class PostsItemLike(

	@field:SerializedName("createdAt")
	val createdAt: CreateAt? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("authorName")
	val authorName: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("authorProfilePicture")
	val authorProfilePicture: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: UpdatedAt? = null,

	@field:SerializedName("likes")
	val likes: List<String?>? = null
)

data class CreateAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)
