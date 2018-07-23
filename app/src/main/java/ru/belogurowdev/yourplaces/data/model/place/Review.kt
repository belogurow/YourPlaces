package ru.belogurowdev.yourplaces.data.model.place

import com.google.gson.annotations.SerializedName

data class Review(
        @SerializedName("author_name")
        var authorName: String? = null,

        @SerializedName("author_url")
        var authorUrl: String? = null,

        @SerializedName("language")
        var language: String? = null,

        @SerializedName("profile_photo_url")
        var profilePhotoUrl: String? = null,

        @SerializedName("rating")
        var rating: Int? = null,

        @SerializedName("relative_time_description")
        var relativeTimeDescription: String? = null,

        @SerializedName("text")
        var text: String? = null,

        @SerializedName("time")
        var time: Int? = null
)
