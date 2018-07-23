package ru.belogurowdev.yourplaces.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.R;
import ru.belogurowdev.yourplaces.adapters.PlaceTypesAdapter;
import ru.belogurowdev.yourplaces.models.PlaceType;
import ru.belogurowdev.yourplaces.util.App;

/**
 * choose type of place
 */
public class PlaceTypesActivity extends AppCompatActivity {

    private List<PlaceType> mPlaceTypes;
    private PlaceTypesAdapter mAdapter;

    @BindView(R.id.toolbar_category) Toolbar mToolbar;
    @BindView(R.id.rv_place_type) RecyclerView mRecyclerView;

    private String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_types);
        ButterKnife.bind(this);

        country = getIntent().getStringExtra("COUNTRY");

        setToolbar();
        setPlacesList();
        setAdapter(country);
        setRecyclerView();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    private void setPlacesList() {
        mPlaceTypes = new ArrayList<>();
        mPlaceTypes.add(new PlaceType(getString(R.string.airport), R.drawable.airport));
        mPlaceTypes.add(new PlaceType(getString(R.string.bar), R.drawable.bar));
        mPlaceTypes.add(new PlaceType(getString(R.string.bank), R.drawable.bank));
        mPlaceTypes.add(new PlaceType(getString(R.string.cafe), R.drawable.cafe));
        mPlaceTypes.add(new PlaceType(getString(R.string.cinema), R.drawable.cinema));
        mPlaceTypes.add(new PlaceType(getString(R.string.hospital), R.drawable.hospital));
        mPlaceTypes.add(new PlaceType(getString(R.string.hotel), R.drawable.hotel));
        mPlaceTypes.add(new PlaceType(getString(R.string.library), R.drawable.library));
        mPlaceTypes.add(new PlaceType(getString(R.string.restaurant), R.drawable.restaurant));
        mPlaceTypes.add(new PlaceType(getString(R.string.mall), R.drawable.mall));
        mPlaceTypes.add(new PlaceType(getString(R.string.museum), R.drawable.museum));
        mPlaceTypes.add(new PlaceType(getString(R.string.parking), R.drawable.parking));
    }

    private void setAdapter(String country) {
        mAdapter = new PlaceTypesAdapter(this, country, mPlaceTypes);
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setItemViewCacheSize(20);
//        mRecyclerView.setDrawingCacheEnabled(true);
//        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        // Save country because its value is lost between activities
        SharedPreferences sharedPreferences = getSharedPreferences("COUNTRY_NAME", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();

        editor.putString("COUNTRY", country);
        editor.apply();
        Log.d("data-onPause", country);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("COUNTRY_NAME", MODE_PRIVATE);

        if (country == null) {
            country = sharedPreferences.getString("COUNTRY", "error");
            mAdapter.setCountry(country);
        }

        Log.d("data-onResume", country);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Finish activity when press back button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.Companion.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
