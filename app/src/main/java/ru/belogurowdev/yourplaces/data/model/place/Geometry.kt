package ru.belogurowdev.yourplaces.data.model.place

import com.google.gson.annotations.SerializedName

data class Geometry(
        @SerializedName("location")
        var location: Location? = null,

        @SerializedName("viewport")
        var viewport: Viewport? = null
)



