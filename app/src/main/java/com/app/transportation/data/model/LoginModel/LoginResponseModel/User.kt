package com.app.transportation.data.model.LoginModel.LoginResponseModel


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("created_at")
    val createdAt: String, // 2023-05-17T11:52:56.000000Z
    @SerializedName("email")
    val email: String, // subadmin@abc.com
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any?, // null
    @SerializedName("dob")
    val dob: String?, // 0000-00-00
    @SerializedName("id")
    val id: Int, // 2
    @SerializedName("gender")
    val gender:String,
    @SerializedName("is_active")
    val isActive: Int, // 1
    @SerializedName("name")
    val name: String, // Supervisor 1
    @SerializedName("phone")
    val phone: String, // 1234567890
    @SerializedName("type")
    val type: Int, // 1
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-05-17T11:52:56.000000Z
    @SerializedName("uuid")
    val uuid: Any? // null
)