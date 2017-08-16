package ru.belogurowdev.yourplaces.Model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alexbelogurow on 05.08.17.
 */

public class Recommendation {
    private String recommendationTitle;
    private List<String> cardTitles;
    private List<Drawable> cardImages;

    public Recommendation(String recommendationTitle) {
        this.recommendationTitle = recommendationTitle;
        this.cardTitles = new ArrayList<>();
        this.cardImages = new ArrayList<>();
    }

    public String getRecommendationTitle() {
        return recommendationTitle;
    }

    public void addCard(String cardTitle, Drawable cardImage) {
        this.cardTitles.add(cardTitle);
        this.cardImages.add(cardImage);
    }

    public String getCardTitle(int number) {
        return cardTitles.get(number);
    }

    public Drawable getCardImage(int number) {
        return cardImages.get(number);
    }

    public int getSize() {
        return cardImages.size();
    }
}
