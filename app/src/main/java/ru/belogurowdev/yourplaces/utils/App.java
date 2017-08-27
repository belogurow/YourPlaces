package ru.belogurowdev.yourplaces.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * Created by alexbelogurow on 26.08.17.
 */

public class App extends Application {

    private static final String TAG = "APP";
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "create");
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        mRefWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.mRefWatcher;
    }

}
