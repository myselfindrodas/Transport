package com.app.transportation.data.model.VehicleModel.VehicleResponseModel


import com.google.gson.annotations.SerializedName

data class VehicleResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String, // Vehicle Data Found
    @SerializedName("response_code")
    val responseCode: Int, // 200
    @SerializedName("status")
    val status: Boolean // true
)