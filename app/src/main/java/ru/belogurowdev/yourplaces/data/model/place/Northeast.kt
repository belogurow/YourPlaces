package ru.belogurowdev.yourplaces.data.model.place

import com.google.gson.annotations.SerializedName

data class Northeast(
        @SerializedName("lat")
        var latitude: Double? = null,

        @SerializedName("lng")
        var longitude: Double? = null
)
