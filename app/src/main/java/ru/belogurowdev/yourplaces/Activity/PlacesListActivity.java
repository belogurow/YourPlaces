package ru.belogurowdev.yourplaces.Activity;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.belogurowdev.yourplaces.Adapter.PlacesListAdapter;
import ru.belogurowdev.yourplaces.Api.ControllerPlacesApi;
import ru.belogurowdev.yourplaces.Api.GooglePlacesModel.GooglePlace;
import ru.belogurowdev.yourplaces.Api.GooglePlacesModel.Result;
import ru.belogurowdev.yourplaces.R;

/**
 * Show list of places
 */
public class PlacesListActivity extends AppCompatActivity {

    private final static String API_KEY = "AIzaSyAuJIEnY4TcR-G67YJSgS2CNbPJNABzs3s";
    private final static String STATUS_OK = "OK";

    private GooglePlace mGooglePlace;
    private List<Result> mPlacesList;
    private PlacesListAdapter mPlaceListAdapter;

    // ExtraString
    private String country, type;

    @BindView(R.id.toolbar_places_list) Toolbar mToolbar;
    @BindView(R.id.rv_places_list) RecyclerView mRecyclerView;
    @BindView(R.id.progressbar_places_list) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);
        ButterKnife.bind(this);

        getExtraDataFromPrevActivity();
        setToolbar();
        setPlaceList();
        setAdapter();
        setRecyclerView();

        loadPlaces();
    }



    private void setToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    private void getExtraDataFromPrevActivity() {
        country = getIntent().getStringExtra("COUNTRY");
        type = getIntent().getStringExtra("TYPE");
    }

    private void setPlaceList() {
        mPlacesList = new ArrayList<>();
    }

    private void setAdapter() {
        mPlaceListAdapter = new PlacesListAdapter(this, mPlacesList);
    }

    private void setRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mPlaceListAdapter);
    }

    /**
     * Load places with retrofit
     */
    private void loadPlaces() {
        Locale currentLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            currentLocale = getResources().getConfiguration().getLocales().get(0);
        } else {
            currentLocale = getResources().getConfiguration().locale;
        }

        ControllerPlacesApi placesApi = new ControllerPlacesApi();
        placesApi.getGooglePlacesApi().getPlaces("in " + country + " " + type, API_KEY, currentLocale.getLanguage()).enqueue(new Callback<GooglePlace>() {
            @Override
            public void onResponse(@NonNull Call<GooglePlace> call, @NonNull Response<GooglePlace> response) {
                mProgressBar.setVisibility(View.GONE);

                if (response.body().getStatus().equals(STATUS_OK)) {
                    mGooglePlace = response.body();
                    Log.d("places-url", call.request().url().toString());
                    mPlacesList.addAll(mGooglePlace.getResults());
                    mPlaceListAdapter.notifyDataSetChanged();
                } else {
                    errorToast(response.body().getStatus());
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
}

