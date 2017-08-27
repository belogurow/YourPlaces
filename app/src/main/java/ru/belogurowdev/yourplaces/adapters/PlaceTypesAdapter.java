package ru.belogurowdev.yourplaces.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.activities.PlacesListActivity;
import ru.belogurowdev.yourplaces.models.PlaceType;
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

        @BindView(R.id.text_place_type) TextView mTextViewType;
        @BindView(R.id.image_place_type) ImageView mImageViewType;
        @BindView(R.id.card_place_type) CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_place_type, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PlaceType placeType = mPlaceTypes.get(position);

        holder.mTextViewType.setText(placeType.getNameType());
        Glide.with(mContext)
                .load(placeType.getImageType())
                .into(holder.mImageViewType);

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), placeType.getImageType());

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
                placesListIntent.putExtra("TYPE", placeType.getNameType());
                mContext.startActivity(placesListIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaceTypes.size();
    }
}
