package ru.belogurowdev.yourplaces.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setToolbar();
        setPlaceSearchFrag();
        setNavDrawer();


        mTextViewMap.setText(R.string.pick_on_the_map);
        mTextViewMap.setTextColor(Color.WHITE);

        Drawable iconPin = new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_pin)
                .color(Color.WHITE)
                .sizeDp(24);
        mImageViewPin.setImageDrawable(iconPin);


    }

    private void setNavDrawer() {
        NavDrawer navDrawer = new NavDrawer(this, mToolbar, CURRENT_POSITION);
        navDrawer.setNavigationDrawer();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
    }

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
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
