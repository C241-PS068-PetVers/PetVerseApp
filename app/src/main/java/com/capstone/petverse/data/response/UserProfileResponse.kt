package com.capstone.petverse.data.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: UserProfile? = null
)

data class UserProfile(

	@field:SerializedName("profilePicture")
	val profilePicture: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("followers")
	val followers: List<String?>? = null,

	@field:SerializedName("following")
	val following: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
