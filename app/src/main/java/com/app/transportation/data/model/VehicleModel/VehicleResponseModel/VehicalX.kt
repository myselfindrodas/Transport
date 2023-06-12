package com.app.transportation.data.model.VehicleModel.VehicleResponseModel


import com.google.gson.annotations.SerializedName

data class VehicalX(
    @SerializedName("car_number")
    val carNumber: String, // PQ23W1234
    @SerializedName("created_at")
    val createdAt: String, // 2023-05-26T07:00:14.000000Z
    @SerializedName("deleted_at")
    val deletedAt: Any?, // null
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("is_active")
    val isActive: Int, // 0
    @SerializedName("qr_code")
    val qrCode: String, // 647058fe33e6b
    @SerializedName("type")
    val type: String, // vehicle
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-05-26T07:00:14.000000Z
    @SerializedName("uuid")
    val uuid: String, // bd72e219-0ff4-4e8d-9be6-bad029ded514
    @SerializedName("vendor_id")
    val vendorId: Int // 1
)