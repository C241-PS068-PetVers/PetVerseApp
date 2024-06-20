package com.capstone.petverse.data.response

import com.google.gson.annotations.SerializedName

data class DetectionResponse(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("result_name")
	val resultName: String? = null,

	@field:SerializedName("percentage")
	val percentage: Any? = null
)
