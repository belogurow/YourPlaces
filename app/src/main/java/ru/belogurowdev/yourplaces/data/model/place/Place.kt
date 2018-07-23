package ru.belogurowdev.yourplaces.data.model.place

import com.google.gson.annotations.SerializedName

data class Place(
        @SerializedName("formatted_address")
        var formattedAddress: String? = null,

        @SerializedName("geometry")
        var geometry: Geometry? = null,

        @SerializedName("icon")
        var icon: String? = null,

        @SerializedName("id")
        var id: String? = null,

        @SerializedName("name")
        var name: String? = null,

        @SerializedName("opening_hours")
        var openingHours: OpeningHours? = null,

        @SerializedName("photos")
        var photos: List<Photo>? = null,

        @SerializedName("place_id")
        var placeId: String? = null,

        @SerializedName("rating")
        var rating: Double? = null,

        @SerializedName("reference")
        var reference: String? = null,

        @SerializedName("types")
        var types: List<String>? = null,

        @SerializedName("price_level")
        var priceLevel: Int? = null
)
