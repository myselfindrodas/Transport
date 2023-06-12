package com.app.transportation.data.model.ClientListModel


import com.google.gson.annotations.SerializedName

data class Pivot(
    @SerializedName("clients_id")
    val clientsId: Int, // 1
    @SerializedName("user_id")
    val userId: Int // 3
)