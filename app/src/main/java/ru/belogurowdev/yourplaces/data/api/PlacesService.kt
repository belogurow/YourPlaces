package ru.belogurowdev.yourplaces.data.api

import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import ru.belogurowdev.yourplaces.data.model.place.PlaceResponse
import ru.belogurowdev.yourplaces.data.model.place.PlacesListResponse

/**
 * Created by alexbelogurow on 10.08.17.
 */

// https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+Sydney&key=YOUR_API_KEY
// https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample/app/src/main/java/com/android/example/github/vo


interface PlacesService {

    @GET("/maps/api/place/textsearch/json")
    fun getPlaces(
            @Query("query") query: String,
            @Query("key") key: String,
            @Query("language") language: String): Deferred<PlacesListResponse>

    @GET("/maps/api/place/details/json")
    fun getPlaceById(
            @Query("placeid") placeId: String,
            @Query("key") key: String,
            @Query("language") language: String): Deferred<PlaceResponse>


}
