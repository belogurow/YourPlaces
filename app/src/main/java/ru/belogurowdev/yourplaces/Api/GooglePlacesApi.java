package ru.belogurowdev.yourplaces.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.belogurowdev.yourplaces.Api.GooglePlaceID.GooglePlaceId;
import ru.belogurowdev.yourplaces.Api.GooglePlacesList.GooglePlacesList;

/**
 * Created by alexbelogurow on 10.08.17.
 */

// https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+Sydney&key=YOUR_API_KEY
public interface GooglePlacesApi {
    @GET("/maps/api/place/textsearch/json")
    Call<GooglePlacesList> getPlaces(
            @Query("query") String query,
            @Query("key") String key,
            @Query("language") String language);

    @GET("/maps/api/place/details/json")
    Call<GooglePlaceId> getPlaceById(
            @Query("placeid") String placeId,
            @Query("key") String key,
            @Query("language") String language);


}
