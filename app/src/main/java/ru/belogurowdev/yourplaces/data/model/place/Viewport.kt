package ru.belogurowdev.yourplaces.data.model.place

import com.google.gson.annotations.SerializedName

data class Viewport(
        @SerializedName("northeast")
        var northeast: Northeast? = null,

        @SerializedName("southwest")
        var southwest: Southwest? = null
)

