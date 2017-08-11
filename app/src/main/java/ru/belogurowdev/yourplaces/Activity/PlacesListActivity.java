package ru.belogurowdev.yourplaces.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.belogurowdev.yourplaces.Adapter.PlaceTypesAdapter;
import ru.belogurowdev.yourplaces.Adapter.PlacesListAdapter;
import ru.belogurowdev.yourplaces.Api.ControllerPlacesApi;
import ru.belogurowdev.yourplaces.Api.GooglePlacesModel.GooglePlace;
import ru.belogurowdev.yourplaces.Api.GooglePlacesModel.Result;
import ru.belogurowdev.yourplaces.R;

public class PlacesListActivity extends AppCompatActivity {

    private final static String API_KEY = "AIzaSyAuJIEnY4TcR-G67YJSgS2CNbPJNABzs3s";
    private final static String STATUS_OK = "OK";
    // https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+Sydney&key=AIzaSyAuJIEnY4TcR-G67YJSgS2CNbPJNABzs3s

    private GooglePlace mGooglePlace;
    private List<Result> mPlacesList;
    private PlacesListAdapter mPlaceListAdapter;

    @BindView(R.id.toolbar_places_list) Toolbar mToolbar;
    @BindView(R.id.rv_places_list) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);
        ButterKnife.bind(this);

        setToolbar();

        // TODO теряется country после возрата с PlaceInfoActivity
        final String country = getIntent().getStringExtra("COUNTRY");
        final String type = getIntent().getStringExtra("TYPE");

        mPlacesList = new ArrayList<>();
        mPlaceListAdapter = new PlacesListAdapter(this, mPlacesList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mPlaceListAdapter);

        ControllerPlacesApi placesApi = new ControllerPlacesApi();
        placesApi.getGooglePlacesApi().getPlaces(country + " " + type, API_KEY).enqueue(new Callback<GooglePlace>() {
            @Override
            public void onResponse(@NonNull Call<GooglePlace> call, @NonNull Response<GooglePlace> response) {
                if (response.body().getStatus().equals(STATUS_OK)) {
                    mGooglePlace = response.body();

                    Log.d("places-url", call.request().url().toString());
                    mPlacesList.addAll(mGooglePlace.getResults());
                    Log.d("place1", mPlacesList.toString());
                    mPlaceListAdapter.notifyDataSetChanged();
                } else {
                    errorToast(mGooglePlace.getStatus());
                }
            }

            @Override
            public void onFailure(Call<GooglePlace> call, Throwable t) {
                errorToast(null);
            }
        });
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

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            //Toast.makeText(this, "toolbar not null", Toast.LENGTH_SHORT).show();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
