package com.app.transportation.data.model.ClientListModel


import com.google.gson.annotations.SerializedName

data class ClientsAlloction(
    @SerializedName("created_at")
    val createdAt: String, // 2023-05-24T08:54:03.000000Z
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
    @SerializedName("pivot")
    val pivot: Pivot,
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-05-24T08:54:03.000000Z
    @SerializedName("uuid")
    val uuid: String // cc91b439-3403-4b52-bc63-5145802fafe4
)