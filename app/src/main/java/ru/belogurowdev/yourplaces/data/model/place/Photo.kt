package ru.belogurowdev.yourplaces.data.model.place

import com.google.gson.annotations.SerializedName

data class Photo(
        @SerializedName("html_attributions")
        var htmlAttributions: List<String>? = null,

        @SerializedName("photo_reference")
        var photoReference: String? = null,

        @SerializedName("width")
        var width: Int? = null,

        @SerializedName("height")
        var height: Int? = null
)

