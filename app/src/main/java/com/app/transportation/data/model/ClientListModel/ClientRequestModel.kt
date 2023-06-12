package com.app.transportation.data.model.ClientListModel

import com.google.gson.annotations.SerializedName

data class ClientRequestModel(
    @SerializedName("superviserId")
    val superviserId: String
)