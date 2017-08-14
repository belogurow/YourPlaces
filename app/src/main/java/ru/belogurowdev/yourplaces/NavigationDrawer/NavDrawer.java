package ru.belogurowdev.yourplaces.NavigationDrawer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ru.belogurowdev.yourplaces.Activity.AboutAppActivity;
import ru.belogurowdev.yourplaces.Activity.MainActivity;
import ru.belogurowdev.yourplaces.R;

/**
 * Created by alexbelogurow on 29.07.17.
 */

//TODO maybe singleton
public class NavDrawer {
    private Activity mActivity;
    private Toolbar mToolbar;
    private int currentPosition;

    public NavDrawer(Activity activity, Toolbar toolbar, int currentPosition) {
        this.mActivity = activity;
        this.mToolbar = toolbar;
        this.currentPosition = currentPosition;
    }

    public void setNavigationDrawer() {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(mActivity)
                .withHeaderBackground(R.drawable.nav_drawer)
                .withSelectionListEnabledForSingleProfile(false)
                .build();

        PrimaryDrawerItem search = new PrimaryDrawerItem()
                .withIdentifier(0)
                .withName(R.string.nav_search)
                .withIcon(GoogleMaterial.Icon.gmd_search)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent searchActivity = new Intent(mActivity, MainActivity.class);
                        mActivity.startActivity(searchActivity);
                        return false;
                    }
                });

        PrimaryDrawerItem history = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.nav_history)
                .withIcon(GoogleMaterial.Icon.gmd_history);

        PrimaryDrawerItem favourites = new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName(R.string.nav_favourites)
                .withIcon(GoogleMaterial.Icon.gmd_favorite);


        SecondaryDrawerItem about = new SecondaryDrawerItem()
                .withIdentifier(3)
                .withName(R.string.about)
                .withIcon(MaterialDesignIconic.Icon.gmi_info)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent infoActivity = new Intent(mActivity, AboutAppActivity.class);
                        mActivity.startActivity(infoActivity);
                        return false;
                    }
                });

        final Drawer drawerResult = new DrawerBuilder()
                .withActivity(mActivity)
                .withToolbar(mToolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        search,
                        history,
                        favourites,
                        new DividerDrawerItem(),
                        about
                )
                .withSelectedItem(currentPosition)
                //.withCloseOnClick(true)
                .build();
    }
}
