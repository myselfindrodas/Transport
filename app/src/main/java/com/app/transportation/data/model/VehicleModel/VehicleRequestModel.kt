package com.app.transportation.data.model.VehicleModel

import com.google.gson.annotations.SerializedName

data class VehicleRequestModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("fromDate")
    val fromDate: String
)