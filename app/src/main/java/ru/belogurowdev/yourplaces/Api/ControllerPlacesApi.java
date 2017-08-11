package ru.belogurowdev.yourplaces.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexbelogurow on 11.08.17.
 */

public class ControllerPlacesApi {
    private static final String BASE_URL = "https://maps.googleapis.com";
    // TODO ??? private static final String API_KEY = "AIzaSyAuJIEnY4TcR-G67YJSgS2CNbPJNABzs3s";

    private static GooglePlacesApi sGooglePlacesApi;
    private Retrofit mRetrofit;

    public ControllerPlacesApi() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sGooglePlacesApi = mRetrofit.create(GooglePlacesApi.class);
    }

    // TODO MAYBE SINGLETON
    public static GooglePlacesApi getGooglePlacesApi() {
        return sGooglePlacesApi;
    }
}
