package ru.belogurowdev.yourplaces.data.api

import ru.belogurowdev.yourplaces.data.model.place.PlaceResponse
import ru.belogurowdev.yourplaces.data.model.place.PlacesListResponse

interface PlacesRemoteRepository {

    suspend fun getPlaces(query: String, language: String): PlacesListResponse

    suspend fun getPlaceById(placeId: String, language: String): PlaceResponse
}
