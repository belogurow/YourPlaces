package ru.belogurowdev.yourplaces.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.Adapter.AndroidLibraryAdapter;
import ru.belogurowdev.yourplaces.Model.AndroidLibrary;
import ru.belogurowdev.yourplaces.NavigationDrawer.NavDrawer;
import ru.belogurowdev.yourplaces.R;

public class AboutAppActivity extends AppCompatActivity {

    private final static int CURRENT_POSITION = 3;


    private List<AndroidLibrary> mLibraries;
    private AndroidLibraryAdapter mAdapter;

    @BindView(R.id.toolbar_about_app) Toolbar mToolbar;
    @BindView(R.id.text_about_developed) TextView mTextViewDeveloped;
    @BindView(R.id.recycler_about_libraries) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        ButterKnife.bind(this);


        setToolbar();
        setNavDrawer();
        setLink();
        setLibraries();
        setAdapter();
        setRecyclerView();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.about);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setNavDrawer() {
        NavDrawer navDrawer = new NavDrawer(this, mToolbar, CURRENT_POSITION);
        navDrawer.setNavigationDrawer();
    }

    private void setLink() {
        mTextViewDeveloped.setLinksClickable(true);
        mTextViewDeveloped.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setLibraries() {
        mLibraries = new ArrayList<>();
        mLibraries.add(new AndroidLibrary(
                getString(R.string.android_iconics_title),
                getString(R.string.android_iconics_description))
        );
        mLibraries.add(new AndroidLibrary(
                getString(R.string.butter_knife_title),
                getString(R.string.butter_knife_description))
        );
        mLibraries.add(new AndroidLibrary(
                getString(R.string.glide_title),
                getString(R.string.glide_description))
        );
        mLibraries.add(new AndroidLibrary(
                getString(R.string.material_drawer_title),
                getString(R.string.material_drawer_description))
        );
        mLibraries.add(new AndroidLibrary(
                getString(R.string.palette_api_title),
                getString(R.string.palette_api_decription))
        );
        mLibraries.add(new AndroidLibrary(
                getString(R.string.retrofit2_title),
                getString(R.string.retrofit2_description))
        );

    }

    private void setAdapter() {
        mAdapter = new AndroidLibraryAdapter(this, mLibraries);
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(mAdapter);
    }

}
