package ru.belogurowdev.yourplaces.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.leakcanary.RefWatcher;

import io.realm.Realm;
import io.realm.exceptions.RealmException;
import ru.belogurowdev.yourplaces.R;
import ru.belogurowdev.yourplaces.util.App;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
            Realm.init(getApplicationContext());
        } catch (RealmException e) {
            Toast.makeText(this, "Initialization error", Toast.LENGTH_SHORT).show();
        }

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, secondsDelayed * 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.Companion.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
