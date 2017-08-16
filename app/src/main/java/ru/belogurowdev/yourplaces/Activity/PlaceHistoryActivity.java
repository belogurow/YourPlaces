package ru.belogurowdev.yourplaces.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.exceptions.RealmException;
import ru.belogurowdev.yourplaces.Adapter.PlacesHistoryAdapter;
import ru.belogurowdev.yourplaces.Adapter.PlacesListAdapter;
import ru.belogurowdev.yourplaces.Model.PlaceRealm;
import ru.belogurowdev.yourplaces.NavigationDrawer.NavDrawer;
import ru.belogurowdev.yourplaces.R;

public class PlaceHistoryActivity extends AppCompatActivity {

    private static final String TAG = PlaceHistoryActivity.class.getSimpleName();
    private static final int CURRENT_POSITION = 1;

    @BindView(R.id.toolbar_places_list) Toolbar mToolbar;
    @BindView(R.id.rv_places_list) RecyclerView mRecyclerView;
    @BindView(R.id.progressbar_places_list) ProgressBar mProgressBar;

    private PlacesHistoryAdapter mAdapter;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);
        ButterKnife.bind(this);

        setToolbar();
        setNavDrawer();
        getRealmResults();



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);




    }

    private void getRealmResults() {
        try {
            mRealm = Realm.getDefaultInstance();
            final RealmResults<PlaceRealm> placeRealms = mRealm
                    .where(PlaceRealm.class)
                    .findAllSorted("mDate", Sort.DESCENDING);

            mProgressBar.setVisibility(View.GONE);
            mAdapter = new PlacesHistoryAdapter(this, placeRealms);
        } catch (RealmException e){
            Log.d(TAG, e.getMessage());
        }
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.nav_history));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setNavDrawer() {
        NavDrawer navDrawer = new NavDrawer(this, mToolbar, CURRENT_POSITION);
        navDrawer.setNavigationDrawer();
    }

    @Override
    protected void onDestroy() {
        if (mRealm != null) {
            mRealm.close();
        }
        super.onDestroy();
    }
}
