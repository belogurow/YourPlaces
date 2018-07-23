package ru.belogurowdev.yourplaces.data.model.place

import com.google.gson.annotations.SerializedName

data class PlacesListResponse(
        @SerializedName("html_attributions")
        var htmlAttributions: List<Any>? = null,

        @SerializedName("next_page_token")
        var nextPageToken: String? = null,

        @SerializedName("results")
        var results: List<Place>? = null,

        @SerializedName("status")
        var status: String? = null
)
