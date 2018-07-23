package ru.belogurowdev.yourplaces.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexbelogurow on 11.08.17.
 */

public class ControllerPlacesApi {
    private static final String BASE_URL = "https://maps.googleapis.com";

    private static PlacesService sPlacesService;
    private Retrofit mRetrofit;

    public ControllerPlacesApi() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sPlacesService = mRetrofit.create(PlacesService.class);
    }

    // TODO MAYBE SINGLETON
    public static PlacesService getGooglePlacesApi() {
        return sPlacesService;
    }
}
