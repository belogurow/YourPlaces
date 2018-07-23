package ru.belogurowdev.yourplaces.data.model.place

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
        @SerializedName("html_attributions")
        var htmlAttributions: List<Any>? = null,

        @SerializedName("result")
        var result: PlaceExplicit? = null,

        @SerializedName("status")
        var status: String? = null
)

