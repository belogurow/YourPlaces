package ru.belogurowdev.yourplaces.data.model.place

import com.google.gson.annotations.SerializedName

data class Period(
        @SerializedName("close")
        var close: Close? = null,

        @SerializedName("open")
        var open: Open? = null
)
