package ru.belogurowdev.yourplaces.Activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.belogurowdev.yourplaces.Adapter.PlaceInfoAdapter;
import ru.belogurowdev.yourplaces.Api.ControllerPlacesApi;
import ru.belogurowdev.yourplaces.Api.GooglePlaceID.GooglePlaceId;
import ru.belogurowdev.yourplaces.Api.GooglePlaceID.Result;
import ru.belogurowdev.yourplaces.Model.PlaceRealm;
import ru.belogurowdev.yourplaces.R;

/**
 * Show place mInfoList
 */
public class PlaceInfoActivity extends AppCompatActivity implements OnConnectionFailedListener {

    private static final String TAG = PlaceInfoActivity.class.getSimpleName();
    private static final String EXTRA_PLACE_ID = "ru.belogurowdev.extras.PLACE_ID";
    private static final String EXTRA_PLACE_PHOTO_URL = "ru.belogurowdev.extras.PLACE_PHOTO_URL";
    private final static String API_KEY = "AIzaSyAI-JOPMs5Yr-NhfbEnf_pNO9jA2bcOCkc";
            //android - "AIzaSyAuJIEnY4TcR-G67YJSgS2CNbPJNABzs3s";
    private final static String STATUS_OK = "OK";

    @BindView(R.id.toolbar_place_info) Toolbar mToolbar;
    @BindView(R.id.collaps_toolbar_place_info) CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.imageView_header) ImageView mImageViewHeader;
    @BindView(R.id.progress_place_info) ProgressBar mProgressBar;
    @BindView(R.id.text_place_info_name) TextView mTextViewPlaceName;
    @BindView(R.id.recycler_info_icon) RecyclerView mRecyclerView;

    private GoogleApiClient mGoogleApiClient;
    private String mPlaceId;
    private String mPlacePhotoUrl;
    private Result mCurrentPlace;
    private PlaceInfoAdapter mAdapter;

    private List<String> mInfoList;
    private List<Drawable> mIconsList;

    private int textColor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        ButterKnife.bind(this);

        setToolbar();
        getExtraDataFromPrevActivity();
        buildApiClient();
        loadPlaceData();
        /*
        if (mPlacePhotoUrl.length() > 0) {
            loadPlacePhoto();
        } */

        //placePhotosAsync(mPlaceId);

        mInfoList = new ArrayList<>();
        mIconsList = new ArrayList<>();

        mAdapter = new PlaceInfoAdapter(this, mInfoList, mIconsList);

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

    private void loadPlaceData() {
        Locale currentLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            currentLocale = getResources().getConfiguration().getLocales().get(0);
        } else {
            currentLocale = getResources().getConfiguration().locale;
        }

        ControllerPlacesApi placesApi = new ControllerPlacesApi();
        //https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJ_73WOomAhYAR6DBLKOsdF6w&key=AIzaSyAuJIEnY4TcR-G67YJSgS2CNbPJNABzs3s

        placesApi.getGooglePlacesApi().getPlaceById(mPlaceId, API_KEY, currentLocale.getLanguage()).enqueue(new Callback<GooglePlaceId>() {
            @Override
            public void onResponse(Call<GooglePlaceId> call, Response<GooglePlaceId> response) {
                mProgressBar.setVisibility(View.GONE);

                if (response.body().getStatus().equals(STATUS_OK)) {
                    mCurrentPlace = response.body().getResult();
                    setPlaceData();
                } else {
                    errorToast(response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<GooglePlaceId> call, Throwable t) {
                errorToast("Cannot load data");
            }
        });
    }


    private void loadPlacePhoto() {
        int width = 720;
        mPlacePhotoUrl = "https://maps.googleapis.com/maps/api/place/photo?"
                + "maxwidth=" + width
                + "&photoreference=" + mPlacePhotoUrl
                + "&key=" + API_KEY;

        Glide.with(this)
                .load(mPlacePhotoUrl)
                .transition(new DrawableTransitionOptions().crossFade(300))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Bitmap bitmap = ((BitmapDrawable)resource).getBitmap();
                        //mImageViewHeader.setImageBitmap(bitmap);
                        return false;
                    }
                })
                .into(mImageViewHeader);


    }


    /*
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
            placeDataAsync(mPlaceId);

        }
    };*/

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


    /**
     * Set place data to mAdapter
     */
    private void setPlaceData() {
        mTextViewPlaceName.setText(mCurrentPlace.getName());
        // if (mCurrentPlace.getPhotos() == null) ==> no photo available
        if (mCurrentPlace.getPhotos() != null) {
            if (mPlacePhotoUrl != null && mPlacePhotoUrl.length() > 0) {
                loadPlacePhoto();
            } else {
                mPlacePhotoUrl = mCurrentPlace.getPhotos().get(0).getPhotoReference();
                loadPlacePhoto();
            }
        }
        mAdapter.setTextColor(textColor);

        // address
        if (mCurrentPlace.getFormattedAddress() != null) {
            mInfoList.add(mCurrentPlace.getFormattedAddress());

            Drawable iconPin = new IconicsDrawable(this)
                    .icon(MaterialDesignIconic.Icon.gmi_pin)
                    .color(textColor)
                    .sizeDp(24);
            mIconsList.add(iconPin);
        }

        // rating
        if (mCurrentPlace.getRating() != null) {
            mInfoList.add(mCurrentPlace.getRating() + "");
            Drawable iconRating = new IconicsDrawable(this)
                    .icon(MaterialDesignIconic.Icon.gmi_star_outline)
                    .color(textColor)
                    .sizeDp(24);
            mIconsList.add(iconRating);
        }

        // phone number
        if (mCurrentPlace.getFormattedPhoneNumber() != null) {
            mInfoList.add(mCurrentPlace.getFormattedPhoneNumber());
            Drawable iconPhone = new IconicsDrawable(this)
                    .icon(MaterialDesignIconic.Icon.gmi_phone)
                    .color(textColor)
                    .sizeDp(24);
            mIconsList.add(iconPhone);
        }

        // site
        if (mCurrentPlace.getWebsite() != null) {
            mInfoList.add(mCurrentPlace.getWebsite());
            Drawable iconWebSite = new IconicsDrawable(this)
                    .icon(MaterialDesignIconic.Icon.gmi_globe_alt)
                    .color(textColor)
                    .sizeDp(24);
            mIconsList.add(iconWebSite);
        }

        if (mCurrentPlace.getPriceLevel() != null) {
            String priceLevel = "";
            switch (mCurrentPlace.getPriceLevel()) {
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
            mInfoList.add(priceLevel);
            Drawable iconType = new IconicsDrawable(this)
                    .icon(MaterialDesignIconic.Icon.gmi_money)
                    .color(textColor)
                    .sizeDp(24);
            mIconsList.add(iconType);
        }

        mProgressBar.setVisibility(View.GONE);
        //saveToRealm();
        mAdapter.notifyDataSetChanged();
    }

    private void saveToRealm() {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    PlaceRealm placeRealm = new PlaceRealm(
                            mCurrentPlace.getId(),
                            mCurrentPlace.getName(),
                            mCurrentPlace.getFormattedAddress(),
                            mCurrentPlace.getRating().floatValue());
                    realm.insertOrUpdate(placeRealm);

                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show();
    }

    public void getExtraDataFromPrevActivity() {
        mPlaceId = getIntent().getStringExtra(EXTRA_PLACE_ID);
        mPlacePhotoUrl = getIntent().getStringExtra(EXTRA_PLACE_PHOTO_URL);
    }

    /**
     * Show error message while loading data
     * @param status
     */
    private void errorToast(String status) {
        String errorMessage;
        if (status == null) {
            errorMessage = "Cannot load data";
        } else {
            errorMessage = "Error with status - " + status;
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
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
}