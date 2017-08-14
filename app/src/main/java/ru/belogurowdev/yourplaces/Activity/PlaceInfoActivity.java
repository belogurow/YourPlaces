package ru.belogurowdev.yourplaces.Activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.Adapter.PlaceInfoAdapter;
import ru.belogurowdev.yourplaces.R;

/**
 * Show place info
 */
public class PlaceInfoActivity extends AppCompatActivity implements OnConnectionFailedListener {

    private static final String TAG = PlaceInfoActivity.class.getSimpleName();
    private static final String EXTRA_PLACE_ID = "ru.belogurowdev.extras.PLACE_ID";

    @BindView(R.id.toolbar_place_info) Toolbar mToolbar;
    @BindView(R.id.collaps_toolbar_place_info) CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.imageView_header) ImageView mImageViewHeader;
    @BindView(R.id.progress_place_info) ProgressBar mProgressBar;
    @BindView(R.id.text_place_info_name) TextView mTextViewPlaceName;
    @BindView(R.id.recycler_info_icon) RecyclerView mRecyclerView;

    private GoogleApiClient mGoogleApiClient;
    private String placeId;
    private Place currentPlace;
    private PlaceInfoAdapter mAdapter;

    private List<String> info;
    private List<Drawable> icons;

    private int textColor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        ButterKnife.bind(this);

        setToolbar();
        getExtraDataFromPrevActivity();
        buildApiClient();
        placePhotosAsync(placeId);

        info = new ArrayList<>();
        icons = new ArrayList<>();

        mAdapter = new PlaceInfoAdapter(this, info, icons);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }


    private void buildApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(Places.GEO_DATA_API)
                .build();
        mGoogleApiClient.connect();


    }

    private void placePhotosAsync(final String placeId) {
        Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {
                    @Override
                    public void onResult(PlacePhotoMetadataResult photos) {
                        if (!photos.getStatus().isSuccess()) {
                            Log.d(TAG, "onResult1");
                            return;
                        }

                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                        if (photoMetadataBuffer.getCount() > 0) {
                            Log.d(TAG, "onResult2");
                            // Display the first bitmap in an ImageView in the size of the view
                            photoMetadataBuffer.get(0)
                                    .getScaledPhoto(mGoogleApiClient, mImageViewHeader.getWidth(),
                                            mImageViewHeader.getHeight())
                                    .setResultCallback(mDisplayPhotoResultCallback);

                        } else {
                            Log.d(TAG, "onResult3");
                            placeDataAsync(placeId);
                        }
                        photoMetadataBuffer.release();


                    }
                });
    }

    private ResultCallback<PlacePhotoResult> mDisplayPhotoResultCallback
            = new ResultCallback<PlacePhotoResult>() {
        @Override
        public void onResult(PlacePhotoResult placePhotoResult) {
            if (!placePhotoResult.getStatus().isSuccess()) {
                Log.d(TAG, "onResult4");
                return;
            }

            Bitmap bitmap = placePhotoResult.getBitmap();
            mImageViewHeader.setImageBitmap(bitmap);

            if (bitmap != null && !bitmap.isRecycled()) {
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        List<Palette.Swatch> swatchList = palette.getSwatches();
                        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();

                        vibrantSwatch = swatchList.get(0);
                        if (vibrantSwatch != null) {
                            int headerColor = vibrantSwatch.getRgb();
                            int statusBarColor = getDarkerColor(headerColor, 0.9f);
                            int titleTextColor = vibrantSwatch.getTitleTextColor();

                            mTextViewPlaceName.setBackgroundColor(headerColor);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                getWindow().setStatusBarColor(statusBarColor);
                            }
                            mCollapsingToolbar.setContentScrimColor(headerColor);
                            mTextViewPlaceName.setTextColor(titleTextColor);
                        }

                        Palette.Swatch lightVibrantSwath = palette.getLightVibrantSwatch();
                        lightVibrantSwath = swatchList.get(1);
                        if (lightVibrantSwath != null) {
                            int backgroundColor = lightVibrantSwath.getRgb();
                            textColor = lightVibrantSwath.getBodyTextColor();
                            mTextViewPlaceName.getRootView().setBackgroundColor(backgroundColor);
                        }

                    }
                });
            }
            Log.d(TAG, "onResult5");
            placeDataAsync(placeId);

        }
    };

    private int getDarkerColor(int color, float factor) {
        int a = Color.alpha( color );
        int r = Color.red( color );
        int g = Color.green( color );
        int b = Color.blue( color );

        return Color.argb( a,
                Math.max( (int)(r * factor), 0 ),
                Math.max( (int)(g * factor), 0 ),
                Math.max( (int)(b * factor), 0 ) );
    }

    private void placeDataAsync(String placeId) {
        Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        if (places.getStatus().isSuccess() && places.getCount() > 0) {
                            currentPlace = places.get(0);
                            loadInfoAboutPlace();
                        } else {
                            Log.e(TAG, "Place not found");
                        }

                        places.release();

                    }
                });
    }

    private void loadInfoAboutPlace() {
        mTextViewPlaceName.setText(currentPlace.getName());
        mAdapter.setTextColor(textColor);

        // address
        if (currentPlace.getAddress().length() > 0) {
            info.add(currentPlace.getAddress().toString());

            Drawable iconPin = new IconicsDrawable(this)
                    .icon(MaterialDesignIconic.Icon.gmi_pin)
                    .color(textColor)
                    .sizeDp(24);
            icons.add(iconPin);
        }

        // rating
        if (currentPlace.getRating() != -1f) {
            info.add(currentPlace.getRating() + "");
            Drawable iconRating = new IconicsDrawable(this)
                    .icon(MaterialDesignIconic.Icon.gmi_star_outline)
                    .color(textColor)
                    .sizeDp(24);
            icons.add(iconRating);
        }

        // phone number
        if (currentPlace.getPhoneNumber().length() > 0) {
            info.add(currentPlace.getPhoneNumber().toString());
            Drawable iconPhone = new IconicsDrawable(this)
                    .icon(MaterialDesignIconic.Icon.gmi_phone)
                    .color(textColor)
                    .sizeDp(24);
            icons.add(iconPhone);
        }

        // site
        if (currentPlace.getWebsiteUri() != null) {
            info.add(currentPlace.getWebsiteUri().toString());
            Drawable iconWebSite = new IconicsDrawable(this)
                    .icon(MaterialDesignIconic.Icon.gmi_globe_alt)
                    .color(textColor)
                    .sizeDp(24);
            icons.add(iconWebSite);
        }

        if (currentPlace.getPriceLevel() != -1f) {
            String priceLevel = "";
            switch (currentPlace.getPriceLevel()) {
                case 0:
                    priceLevel = getString(R.string.free);
                    break;
                case 1:
                    priceLevel = getString(R.string.inexpensive);
                    break;
                case 2:
                    priceLevel = getString(R.string.moderate);
                    break;
                case 3:
                    priceLevel = getString(R.string.expensive);
                    break;
                case 4:
                    priceLevel = getString(R.string.very_expensive);
                    break;
            }
            info.add(priceLevel);
            Drawable iconType = new IconicsDrawable(this)
                    .icon(MaterialDesignIconic.Icon.gmi_money)
                    .color(textColor)
                    .sizeDp(24);
            icons.add(iconType);
        }

        mProgressBar.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, connectionResult.toString(), Toast.LENGTH_SHORT).show();
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

    public void getExtraDataFromPrevActivity() {
        placeId = getIntent().getStringExtra(EXTRA_PLACE_ID);
    }
}
