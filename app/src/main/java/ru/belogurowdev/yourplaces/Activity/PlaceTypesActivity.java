package ru.belogurowdev.yourplaces.Activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;

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

    @BindView(R.id.category_toolbar) Toolbar mToolbar;
    @BindView(R.id.rv_place_type) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_types);
        ButterKnife.bind(this);

        setToolbar();

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

        /*
        mPlaceTypes.add(new PlaceType("Atm", getIcon(MaterialDesignIconic.Icon.gmi_local_atm)));
        mPlaceTypes.add(new PlaceType("Pharmacy", getIcon(MaterialDesignIconic.Icon.gmi_local_pharmacy)));
        */

        mAdapter = new PlaceTypesAdapter(this, mPlaceTypes);

        LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);


                /* Drawable iconPin = new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_pin)
                .color(Color.WHITE)
                .sizeDp(24);
        mImageViewPin.setImageDrawab
        */


    }


    private void setToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    private Drawable getIcon(MaterialDesignIconic.Icon icon) {
        return new IconicsDrawable(this)
                .icon(icon)
                .color(Color.BLACK)
                .sizeDp(24);
    }
}
