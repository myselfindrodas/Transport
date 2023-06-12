package com.app.transportation.data.model.ProfileDetailsModel

import com.google.gson.annotations.SerializedName

data class ProfileDetailsRequestModel (
    @SerializedName("user_id")
    val user_id: String
)