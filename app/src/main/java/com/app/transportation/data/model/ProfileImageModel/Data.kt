package com.app.transportation.data.model.ProfileImageModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("img_name")
    val imgName: String, // 1685091763.png
    @SerializedName("url")
    val url: String // http://166.62.54.122/transportation/transportation_app/storage/carInOut
)