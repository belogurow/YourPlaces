package ru.belogurowdev.yourplaces.Model;

/**
 * Created by alexbelogurow on 14.08.17.
 */

public class AndroidLibrary {
    private String title;
    private String description;

    public AndroidLibrary(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}