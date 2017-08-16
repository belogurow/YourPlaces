package ru.belogurowdev.yourplaces.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alexbelogurow on 14.08.17.
 */

public class PlaceRealm extends RealmObject{

    @PrimaryKey
    private long id;

    private String placeId;
    private String placeName;
    private String placeAddress;
    private float placeRating;
    //private String photoReference;

    private boolean isFavorite;

    public PlaceRealm() {
    }

    public PlaceRealm(String placeId, String placeName, String placeAddress, float placeRating) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeRating = placeRating;
        //this.photoReference = photoReference;
        this.isFavorite = false;
    }

    public PlaceRealm(String placeId, String placeName, String placeAddress, float placeRating, boolean isFavorite) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeRating = placeRating;
        //this.photoReference = photoReference;
        this.isFavorite = isFavorite;
    }


}
