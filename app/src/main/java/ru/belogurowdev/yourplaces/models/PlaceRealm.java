package ru.belogurowdev.yourplaces.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alexbelogurow on 14.08.17.
 */

public class PlaceRealm extends RealmObject{

    //@PrimaryKey
    //private long id;

    @PrimaryKey
    private String placeId;
    private String placeName;
    private String placeAddress;
    private double placeRating;
    private String photoReference;
    private Date mDate;

    private boolean isFavorite;

    public PlaceRealm() {
    }

    public PlaceRealm(String placeId, String placeName, String placeAddress, double placeRating, String photoReference, Date date) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeRating = placeRating;
        this.photoReference = photoReference;
        this.isFavorite = false;
        this.mDate = date;
    }

    public PlaceRealm(String placeId, String placeName, String placeAddress, double placeRating, String photoReference, Date date, boolean isFavorite) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeRating = placeRating;
        this.photoReference = photoReference;
        this.mDate = date;
        this.isFavorite = isFavorite;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public double getPlaceRating() {
        return placeRating;
    }

    public void setPlaceRating(double placeRating) {
        this.placeRating = placeRating;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "PlaceRealm{" +
                "placeId='" + placeId + '\'' +
                ", placeName='" + placeName + '\'' +
                ", placeAddress='" + placeAddress + '\'' +
                ", placeRating=" + placeRating +
                ", photoReference='" + photoReference + '\'' +
                ", mDate=" + mDate +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
