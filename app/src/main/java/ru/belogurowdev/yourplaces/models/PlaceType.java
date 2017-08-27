package ru.belogurowdev.yourplaces.models;

/**
 * Created by alexbelogurow on 06.08.17.
 */

public class PlaceType {
    private String mNameType;
    private int mImageType;

    public PlaceType(String mNameType, int mImageType) {
        this.mNameType = mNameType;
        this.mImageType = mImageType;
    }


    public String getNameType() {
        return mNameType;
    }

    public void setNameType(String nameType) {
        this.mNameType = nameType;
    }

    public int getImageType() {
        return mImageType;
    }

    public void setImageType(int imageType) {
        this.mImageType = imageType;
    }
}
