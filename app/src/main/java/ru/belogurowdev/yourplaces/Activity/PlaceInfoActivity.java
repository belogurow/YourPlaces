package ru.belogurowdev.yourplaces.Activity;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;


import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.R;

public class PlaceInfoActivity extends AppCompatActivity implements OnConnectionFailedListener {

    @BindView(R.id.toolbar_placeinfo) Toolbar mToolbar;
    @BindView(R.id.imageView_header) ImageView mImageViewHeader;
    @BindView(R.id.textView_test) TextView mTextViewTest;

    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "place info";



    Place myPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        ButterKnife.bind(this);

        final String placeName = getIntent().getStringExtra("PLACE_NAME");
        final String placeID = getIntent().getStringExtra("PLACE_ID");

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(placeName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        buildApiClient();
        placePhotosAsync(placeID);
        Log.i(TAG, placeID);
        Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeID)
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        Log.i(TAG, "2");
                        if (places.getStatus().isSuccess() && places.getCount() > 0) {
                            final Place place = places.get(0);
                            myPlace = place;
                            Log.i(TAG, myPlace.getName() + " 1 " + myPlace.getAddress());
                        } else {
                            Log.e(TAG, "Place not found");
                        }

                        mTextViewTest.setText(myPlace.getName() + " " + myPlace.getAddress());
                        places.release();


                    }
                });



        Log.i(TAG, "3 ");
        /*
        Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, placeID)
                .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {
                    @Override
                    public void onResult(@NonNull PlacePhotoMetadataResult placePhotoMetadataResult) {

                    }
                });
                */

        /*
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("test1");
        }
        */


    }


    private void buildApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(Places.GEO_DATA_API)
                .build();
        mGoogleApiClient.connect();


    }

    private ResultCallback<PlacePhotoResult> mDisplayPhotoResultCallback
            = new ResultCallback<PlacePhotoResult>() {
        @Override
        public void onResult(PlacePhotoResult placePhotoResult) {
            if (!placePhotoResult.getStatus().isSuccess()) {
                return;
            }
            mImageViewHeader.setImageBitmap(placePhotoResult.getBitmap());
        }
    };

    private void placePhotosAsync(String placeID) {
        Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, placeID)
                .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {


                    @Override
                    public void onResult(PlacePhotoMetadataResult photos) {
                        if (!photos.getStatus().isSuccess()) {
                            return;
                        }

                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                        if (photoMetadataBuffer.getCount() > 0) {
                            // Display the first bitmap in an ImageView in the size of the view
                            photoMetadataBuffer.get(0)
                                    .getScaledPhoto(mGoogleApiClient, mImageViewHeader.getWidth(),
                                            mImageViewHeader.getHeight())
                                    .setResultCallback(mDisplayPhotoResultCallback);
                        }
                        photoMetadataBuffer.release();
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // TODO show error
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
