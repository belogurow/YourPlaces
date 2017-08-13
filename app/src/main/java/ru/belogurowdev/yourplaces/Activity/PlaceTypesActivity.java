package ru.belogurowdev.yourplaces.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.Adapter.PlaceTypesAdapter;
import ru.belogurowdev.yourplaces.Model.PlaceType;
import ru.belogurowdev.yourplaces.R;

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

    private void setRecyclerView() {
        LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setAdapter(String country) {
        mAdapter = new PlaceTypesAdapter(this, country, mPlaceTypes);
    }

    private void setPlacesList() {
        mPlaceTypes = new ArrayList<>();
        mPlaceTypes.add(new PlaceType("Bar", R.drawable.bar));
        mPlaceTypes.add(new PlaceType("Hospital", R.drawable.hospital));
        mPlaceTypes.add(new PlaceType("Library", R.drawable.library));
        mPlaceTypes.add(new PlaceType("Hotel", R.drawable.hotel));
        mPlaceTypes.add(new PlaceType("Parking", R.drawable.parking));
        mPlaceTypes.add(new PlaceType("Cinema", R.drawable.cinema));
        mPlaceTypes.add(new PlaceType("Bank", R.drawable.bank));
        mPlaceTypes.add(new PlaceType("Cafe", R.drawable.cafe));
        mPlaceTypes.add(new PlaceType("Airport", R.drawable.airport));
        mPlaceTypes.add(new PlaceType("Mall", R.drawable.mall));
    }


    private void setToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
}
