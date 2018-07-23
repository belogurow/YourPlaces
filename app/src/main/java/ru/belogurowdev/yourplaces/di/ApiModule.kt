package ru.belogurowdev.yourplaces.di

import android.support.annotation.NonNull
import dagger.Module
import dagger.Provides
import ru.belogurowdev.yourplaces.data.api.PlacesRemoteRepository
import ru.belogurowdev.yourplaces.data.api.PlacesService
import ru.belogurowdev.yourplaces.data.api.RetrofitFactory
import ru.belogurowdev.yourplaces.util.App
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @NonNull
    @Singleton
    fun providePlacesService() : PlacesService = RetrofitFactory.createService(true, App.BASE_URL)

    @Provides
    @NonNull
    @Singleton
    fun providePlacesRemoteRepository(placesService: PlacesService) : PlacesRemoteRepository = PlacesRemoteRepositoryImpl(placesService)
}