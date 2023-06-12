package com.app.transportation.data.model.LoginModel.LoginResponseModel


import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String, // User Details Found
    @SerializedName("response_code")
    val responseCode: Int, // 200
    @SerializedName("status")
    val status: Boolean // true
)