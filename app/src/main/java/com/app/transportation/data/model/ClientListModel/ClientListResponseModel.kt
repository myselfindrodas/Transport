package com.app.transportation.data.model.ClientListModel


import com.google.gson.annotations.SerializedName

data class ClientListResponseModel(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String, // Client Data Found
    @SerializedName("response_code")
    val responseCode: Int, // 200
    @SerializedName("status")
    val status: Boolean // true
)