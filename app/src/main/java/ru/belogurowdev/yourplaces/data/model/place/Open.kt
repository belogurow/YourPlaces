package ru.belogurowdev.yourplaces.data.model.place

import com.google.gson.annotations.SerializedName

data class Open(
        @SerializedName("day")
        var day: Int? = null,

        @SerializedName("time")
        var time: String? = null
)

