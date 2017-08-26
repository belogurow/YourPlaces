package ru.belogurowdev.yourplaces.utils;

/**
 * Created by alexbelogurow on 26.08.17.
 */

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * //TODO add it to other classes
 * Firebase analytics class
 */
public class FBAnalytics {
    private static FirebaseAnalytics mFirebaseAnalytics;

    public static FirebaseAnalytics getInstance(Context context) {
        if (mFirebaseAnalytics == null) {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        }
        return mFirebaseAnalytics;
    }
}
