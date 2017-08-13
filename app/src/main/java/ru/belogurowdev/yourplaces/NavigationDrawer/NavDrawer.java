package ru.belogurowdev.yourplaces.NavigationDrawer;

import android.app.Activity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import ru.belogurowdev.yourplaces.R;

/**
 * Created by alexbelogurow on 29.07.17.
 */

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
                .withIcon(GoogleMaterial.Icon.gmd_search);

        PrimaryDrawerItem history = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.nav_history)
                .withIcon(GoogleMaterial.Icon.gmd_history);

        PrimaryDrawerItem favourites = new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName(R.string.nav_favourites)
                .withIcon(GoogleMaterial.Icon.gmd_favorite);


        SecondaryDrawerItem openSource = new SecondaryDrawerItem()
                .withIdentifier(3)
                .withName(R.string.nav_openSource)
                .withIcon(FontAwesome.Icon.faw_github);

        SecondaryDrawerItem contact = new SecondaryDrawerItem()
                .withIdentifier(4)
                .withName(R.string.nav_contact)
                .withIcon(MaterialDesignIconic.Icon.gmi_mail_send);

        final Drawer drawerResult = new DrawerBuilder()
                .withActivity(mActivity)
                .withToolbar(mToolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        search,
                        history,
                        favourites,
                        new DividerDrawerItem(),
                        openSource,
                        contact
                )
                .withSelectedItem(currentPosition)
                .withCloseOnClick(true)
                .build();
    }
}
