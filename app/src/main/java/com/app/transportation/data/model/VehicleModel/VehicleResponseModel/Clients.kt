package com.app.transportation.data.model.VehicleModel.VehicleResponseModel


import com.google.gson.annotations.SerializedName

data class Clients(
    @SerializedName("created_at")
    val createdAt: String, // 2023-05-26T06:59:44.000000Z
    @SerializedName("deleted_at")
    val deletedAt: Any?, // null
    @SerializedName("email")
    val email: String, // client@abc.com
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("is_active")
    val isActive: Int, // 0
    @SerializedName("name")
    val name: String, // client1
    @SerializedName("phone")
    val phone: Int, // 1234567890
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-05-26T06:59:44.000000Z
    @SerializedName("uuid")
    val uuid: String // b9af64fe-bc46-4503-ad1f-ff469795bad5
)