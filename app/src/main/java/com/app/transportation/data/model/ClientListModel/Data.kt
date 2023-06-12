package com.app.transportation.data.model.ClientListModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("clients_alloction")
    val clientsAlloction: List<ClientsAlloction>,
    @SerializedName("created_at")
    val createdAt: String, // 2023-05-24T09:40:56.000000Z
    @SerializedName("email")
    val email: String, // supervisor@abc.com
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any?, // null
    @SerializedName("id")
    val id: Int, // 3
    @SerializedName("img")
    val img: Any?, // null
    @SerializedName("is_active")
    val isActive: Int, // 1
    @SerializedName("name")
    val name: String, // supervisor
    @SerializedName("phone")
    val phone: String, // 1234567890
    @SerializedName("type")
    val type: Int, // 1
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-05-24T09:40:56.000000Z
    @SerializedName("uuid")
    val uuid: String // 8e941b28-673a-4835-8974-dfdcfc216dff
)