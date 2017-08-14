package ru.belogurowdev.yourplaces.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

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

    private static final String EXTRA_PLACE_ID = "ru.belogurowdev.extras.PLACE_ID";

    final static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar_main) Toolbar mToolbar;
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

    /**
     * Create new recommendation list with horizontal sub-list category
     */
    private void setRecommendationList() {
        // first recommendation
        Recommendation countryRecommendation = new Recommendation(getString(R.string.countries_for_tourism));
        countryRecommendation.addCard(getString(R.string.Usa), null);
        countryRecommendation.addCard(getString(R.string.Italy), null);
        countryRecommendation.addCard(getString(R.string.France), null);
        countryRecommendation.addCard(getString(R.string.Russia), null);

        setNewRecommendation(countryRecommendation, include1);

        // second recommendation TODO test
        Recommendation placesRecommendation = new Recommendation("Places for everyone");
        placesRecommendation.addCard("Bar", null);
        placesRecommendation.addCard("Cafe", null);

        setNewRecommendation(placesRecommendation, include2);
    }

    /**
     * Initialization a tittle of recommendation and recycler view
     * @param newRecommendation recommendation
     * @param parentView view that included in activity_main
     */
    private void setNewRecommendation(final Recommendation newRecommendation, final View parentView) {
        RecyclerView mRecyclerView = parentView.findViewById(R.id.rv_recommendations);
        TextView mTextViewTittle = parentView.findViewById(R.id.textView_rv_recomend_tittle);
        mTextViewTittle.setText(newRecommendation.getRecommendationTitle());

        RecommendAdapter adapter = new RecommendAdapter(this, newRecommendation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
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
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        if (autocompleteFragment.getView() != null) {
            autocompleteFragment.getView().setBackgroundColor(Color.WHITE);

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    startPlaceInfoActivity(place.getId());
                    autocompleteFragment.setText("");
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
                startPlaceInfoActivity(place.getId());
            }
        }
    }

    private void startPlaceInfoActivity(String placeId) {
        Intent placeInfo = new Intent(this, PlaceInfoActivity.class);
        placeInfo.putExtra(EXTRA_PLACE_ID, placeId);
        startActivity(placeInfo);
    }
}
