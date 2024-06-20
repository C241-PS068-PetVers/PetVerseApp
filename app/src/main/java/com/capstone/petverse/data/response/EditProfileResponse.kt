package com.capstone.petverse.data.response

import com.google.gson.annotations.SerializedName

data class EditProfileResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: EditUser? = null
)

data class EditUser(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("refreshToken")
	val refreshToken: String? = null,

	@field:SerializedName("profilePicture")
	val profilePicture: String? = null
)
