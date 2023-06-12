package com.app.transportation.data.model.ProfileDetailsModel.ProfileDetailsResponseModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("url")
    val url: String, // http://166.62.54.122/transportation/transportation_app/storage/carInOut
    @SerializedName("user")
    val user: List<User>
)