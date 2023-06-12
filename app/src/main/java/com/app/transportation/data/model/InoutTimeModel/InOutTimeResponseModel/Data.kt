package com.app.transportation.data.model.InoutTimeModel.InOutTimeResponseModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("car_date")
    val carDate: String, // 2023-05-25T08:21:23.482669Z
    @SerializedName("clients_id")
    val clientsId: String, // 1
    @SerializedName("created_at")
    val createdAt: String, // 2023-05-25T08:21:23.000000Z
    @SerializedName("id")
    val id: Int, // 8
    @SerializedName("in_time")
    val inTime: String, // 20:14:00
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-05-25T08:21:23.000000Z
    @SerializedName("users_id")
    val usersId: Int, // 3
    @SerializedName("uuid")
    val uuid: String, // a738c335-74f7-417f-9f33-1560e9092127
    @SerializedName("vehicles_id")
    val vehiclesId: Int // 1
)