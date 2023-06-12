package com.app.transportation.data.model.VehicleModel.VehicleResponseModel


import com.google.gson.annotations.SerializedName

data class Vehical(
    @SerializedName("car_date")
    val carDate: String, // 2023-05-25
    @SerializedName("clients")
    val clients: Clients,
    @SerializedName("clients_id")
    val clientsId: Int, // 1
    @SerializedName("created_at")
    val createdAt: String, // 2023-05-26T07:08:34.000000Z
    @SerializedName("deleted_at")
    val deletedAt: Any?, // null
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("in_time")
    val inTime: String, // 12:38:31
    @SerializedName("in_time_img")
    val inTimeImg: Any?, // null
    @SerializedName("is_active")
    val isActive: Int, // 0
    @SerializedName("out_time")
    val outTime: Any?, // null
    @SerializedName("out_time_img")
    val outTimeImg: Any?, // null
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-05-26T07:08:34.000000Z
    @SerializedName("users_id")
    val usersId: Int, // 2
    @SerializedName("uuid")
    val uuid: String, // c16cc8a7-e9d8-4c00-bd8b-d269bf38acb0
    @SerializedName("vehical")
    val vehical: VehicalX,
    @SerializedName("vehicles_id")
    val vehiclesId: Int // 1
)