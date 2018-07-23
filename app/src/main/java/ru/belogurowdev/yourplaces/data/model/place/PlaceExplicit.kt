package ru.belogurowdev.yourplaces.data.model.place

import com.google.gson.annotations.SerializedName

import io.realm.RealmModel

data class PlaceExplicit(
        @SerializedName("address_components")
        var addressComponents: List<AddressComponent>? = null,

        @SerializedName("adr_address")
        var adrAddress: String? = null,

        @SerializedName("formatted_address")
        var formattedAddress: String? = null,

        @SerializedName("formatted_phone_number")
        var formattedPhoneNumber: String? = null,

        @SerializedName("geometry")
        var geometry: Geometry? = null,

        @SerializedName("icon")
        var icon: String? = null,

        @SerializedName("id")
        var id: String? = null,

        @SerializedName("international_phone_number")
        var internationalPhoneNumber: String? = null,

        @SerializedName("name")
        var name: String? = null,

        @SerializedName("opening_hours")
        var openingHours: OpeningHours? = null,

        @SerializedName("photos")
        var photos: List<Photo>? = null,

        @SerializedName("place_id")
        var placeId: String? = null,

        @SerializedName("price_level")
        var priceLevel: Int? = null,

        @SerializedName("rating")
        var rating: Double? = null,

        @SerializedName("reference")
        var reference: String? = null,

        @SerializedName("reviews")
        var reviews: List<Review>? = null,

        @SerializedName("scope")
        var scope: String? = null,

        @SerializedName("types")
        var types: List<String>? = null,

        @SerializedName("url")
        var url: String? = null,

        @SerializedName("utc_offset")
        var utcOffset: Int? = null,

        @SerializedName("vicinity")
        var vicinity: String? = null,

        @SerializedName("website")
        var website: String? = null
) : RealmModel 
