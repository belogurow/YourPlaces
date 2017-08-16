package ru.belogurowdev.yourplaces.Model;

import android.graphics.drawable.Drawable;

/**
 * Created by alexbelogurow on 06.08.17.
 */

public class PlaceType {
    private String type;
    private int backgroundImage;

    public PlaceType(String type, int backgroundImage) {
        this.type = type;
        this.backgroundImage = backgroundImage;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(int backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
