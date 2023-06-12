package com.app.transportation.data.model.ProfileImageModel


import com.google.gson.annotations.SerializedName

data class ProfileImageResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String, // Profile  Image Updated successfully
    @SerializedName("response_code")
    val responseCode: Int, // 200
    @SerializedName("status")
    val status: Boolean // true
)