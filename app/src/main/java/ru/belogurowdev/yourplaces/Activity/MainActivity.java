package ru.belogurowdev.yourplaces.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.belogurowdev.yourplaces.Adapter.RecommendAdapter;
import ru.belogurowdev.yourplaces.Model.Recommendation;
import ru.belogurowdev.yourplaces.NavigationDrawer.NavDrawer;
import ru.belogurowdev.yourplaces.R;

public class MainActivity extends AppCompatActivity {

    final static int CURRENT_POSITION = 0;
    final static int PLACE_PICKER_REQUEST = 1;
    final static String TAG = "info";

    @BindView(R.id.main_toolbar) Toolbar mToolbar;
    @BindView(R.id.textView_main_map) TextView mTextViewMap;
    @BindView(R.id.imageView_main_pin) ImageView mImageViewPin;
    @BindView(R.id.imageView_main_map) ImageView mImageViewMap;

    @BindView(R.id.include1) View include1;
    @BindView(R.id.include2) View include2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setToolbar();
        setPlaceSearchFrag();
        setNavDrawer();
        setPlacePicker();
        setRecommendationList();


    }

    private void setRecommendationList() {
        // first
        RecyclerView mRecyclerView = include1.findViewById(R.id.recycler_view_recommendations);
        List<Recommendation> recommendationList = new ArrayList<>();
        for (int i = 0; i != 5; i++){
            recommendationList.add(new Recommendation());
        }
        RecommendAdapter adapter = new RecommendAdapter(this, recommendationList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setBackgroundColor(Color.BLUE);

        // second
        RecyclerView mRecyclerView2 = include2.findViewById(R.id.recycler_view_recommendations);
        List<Recommendation> recommendationList2 = new ArrayList<>();
        for (int i = 0; i != 3; i++){
            recommendationList2.add(new Recommendation());
        }
        RecommendAdapter adapter2 = new RecommendAdapter(this, recommendationList2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView2.setLayoutManager(layoutManager2);
        mRecyclerView2.setAdapter(adapter2);
        mRecyclerView2.setBackgroundColor(Color.BLACK);

    }

    private void setNavDrawer() {
        NavDrawer navDrawer = new NavDrawer(this, mToolbar, CURRENT_POSITION);
        navDrawer.setNavigationDrawer();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
    }

    /**
     * PlaceAutoComplete configuration
     */
    private void setPlaceSearchFrag() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        if (autocompleteFragment.getView() != null) {
            autocompleteFragment.getView().setBackgroundColor(Color.WHITE);

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    Log.i(TAG, "Place: " + place.getName() + place.getPriceLevel() + " " + place.getPlaceTypes());
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
        }
    }

    /**
     * Place picker configuration, image with text and icon
     */
    private void setPlacePicker() {
        mTextViewMap.setText(R.string.pick_on_the_map);
        mTextViewMap.setTextColor(Color.WHITE);

        Drawable iconPin = new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_pin)
                .color(Color.WHITE)
                .sizeDp(24);
        mImageViewPin.setImageDrawable(iconPin);
    }

    @OnClick(R.id.imageView_main_map)
    public void openMap() {


        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                // TODO new intent
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                Intent placeInfo = new Intent(this, PlaceInfoActivity.class);
                placeInfo.putExtra("PLACE_ID", place.getId());
                placeInfo.putExtra("PLACE_NAME", place.getName());
                startActivity(placeInfo);
            }
        }
    }
}
