package com.app.transportation.data.model.InoutTimeModel

import com.google.gson.annotations.SerializedName

data class InOutTimeRequestModel(
    @SerializedName("status")
    val status: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("car_date")
    val car_date: String,
    @SerializedName("users_id")
    val users_id: String,
    @SerializedName("clients_id")
    val clients_id: String,
    @SerializedName("uuid")
    val uuid: String
)