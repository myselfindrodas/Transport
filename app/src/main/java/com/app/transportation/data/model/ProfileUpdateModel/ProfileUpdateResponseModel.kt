package com.app.transportation.data.model.ProfileUpdateModel


import com.google.gson.annotations.SerializedName

data class ProfileUpdateResponseModel(
    @SerializedName("data")
    val `data`: Boolean, // true
    @SerializedName("message")
    val message: String, // Profile Updated successfully
    @SerializedName("response_code")
    val responseCode: Int, // 200
    @SerializedName("status")
    val status: Boolean // true
)