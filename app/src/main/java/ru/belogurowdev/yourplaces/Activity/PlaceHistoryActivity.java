package ru.belogurowdev.yourplaces.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import ru.belogurowdev.yourplaces.Model.PlaceRealm;
import ru.belogurowdev.yourplaces.NavigationDrawer.NavDrawer;
import ru.belogurowdev.yourplaces.R;

public class PlaceHistoryActivity extends AppCompatActivity {

    private static final String TAG = PlaceHistoryActivity.class.getSimpleName();
    private static final int CURRENT_POSITION = 1;

    @BindView(R.id.toolbar_places_list) Toolbar mToolbar;
    @BindView(R.id.rv_places_list) RecyclerView mRecyclerView;
    @BindView(R.id.progressbar_places_list) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);
        ButterKnife.bind(this);

        setToolbar();
        setNavDrawer();


        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            final RealmResults<PlaceRealm> placeRealms = realm.where(PlaceRealm.class).findAll();
            Log.d(TAG, placeRealms.toString());

        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setNavDrawer() {
        NavDrawer navDrawer = new NavDrawer(this, mToolbar, CURRENT_POSITION);
        navDrawer.setNavigationDrawer();
    }
}
