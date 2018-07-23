package ru.belogurowdev.yourplaces.data.model.place

import com.google.gson.annotations.SerializedName

data class OpeningHours(
        @SerializedName("open_now")
        var openNow: Boolean? = null,

        @SerializedName("weekday_text")
        var weekdayText: List<Any>? = null,

        @SerializedName("periods")
        var periods: List<Period>? = null
)
