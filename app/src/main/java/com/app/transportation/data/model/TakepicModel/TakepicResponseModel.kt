package com.app.transportation.data.model.TakepicModel


import com.google.gson.annotations.SerializedName

data class TakepicResponseModel(
    @SerializedName("data")
    val `data`: List<Any>,
    @SerializedName("message")
    val message: String, // Out-Time Image Updated successfully
    @SerializedName("response_code")
    val responseCode: Int, // 200
    @SerializedName("status")
    val status: Boolean // true
)