package ru.belogurowdev.yourplaces.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.Activity.PlacesListActivity;
import ru.belogurowdev.yourplaces.Model.PlaceType;
import ru.belogurowdev.yourplaces.R;

/**
 * Created by alexbelogurow on 06.08.17.
 */

public class PlaceTypesAdapter extends RecyclerView.Adapter<PlaceTypesAdapter.ViewHolder> {
    private Context mContext;
    private String country;
    private List<PlaceType> mPlaceTypes;

    public PlaceTypesAdapter(Context context, String country, List<PlaceType> placeTypes) {
        this.mContext = context;
        this.country = country;
        this.mPlaceTypes = placeTypes;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView_card_type) TextView mTextViewType;
        @BindView(R.id.imageView_card_background) ImageView mImageViewBackground;
        @BindView(R.id.card_place_type) CardView mCardView;


        public static final int DEFAULT_COLOR = 0x000000;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_place_type, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PlaceType placeType = mPlaceTypes.get(position);

        holder.mTextViewType.setText(placeType.getType());

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), placeType.getBackgroundImage());
        holder.mImageViewBackground.setImageBitmap(bitmap);

        if (bitmap != null && !bitmap.isRecycled()) {
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    List<Palette.Swatch> vibrantSwatch = palette.getSwatches();
                    holder.mCardView.setCardBackgroundColor(vibrantSwatch.get(1).getRgb());
                    holder.mTextViewType.setTextColor(vibrantSwatch.get(1).getTitleTextColor());
                }
            });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent placesListIntent = new Intent(mContext, PlacesListActivity.class);
                placesListIntent.putExtra("COUNTRY", country);
                placesListIntent.putExtra("TYPE", placeType.getType());
                mContext.startActivity(placesListIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaceTypes.size();
    }
}
