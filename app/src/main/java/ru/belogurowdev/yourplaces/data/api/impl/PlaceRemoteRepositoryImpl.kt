package ru.belogurowdev.yourplaces.data.api.impl

import ru.belogurowdev.yourplaces.data.api.PlacesRemoteRepository
import ru.belogurowdev.yourplaces.data.api.PlacesService
import ru.belogurowdev.yourplaces.util.App

class PlaceRemoteRepositoryImpl(private val placesService: PlacesService) : PlacesRemoteRepository {

    override suspend fun getPlaces(query: String, language: String) = placesService.getPlaces(query, App.API_KEY, language).await()

    override suspend fun getPlaceById(placeId: String, language: String) = placesService.getPlaceById(placeId, App.API_KEY, language).await()

}