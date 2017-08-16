package ru.belogurowdev.yourplaces.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.Activity.PlaceInfoActivity;
import ru.belogurowdev.yourplaces.Api.GooglePlacesModel.Result;
import ru.belogurowdev.yourplaces.R;

/**
 * Created by alexbelogurow on 11.08.17.
 */

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.ViewHolder> {

    private final static String API_KEY = "AIzaSyAuJIEnY4TcR-G67YJSgS2CNbPJNABzs3s";
    private static final String EXTRA_PLACE_ID = "ru.belogurowdev.extras.PLACE_ID";

    private Context mContext;
    private List<Result> mPlacesList;

    public PlacesListAdapter(Context context,@Nullable List<Result> placesList) {
        mContext = context;
        mPlacesList = placesList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_place_info) CardView mCardView;
        @BindView(R.id.image_card_place_info_photo) ImageView mImageViewPlacePhoto;
        @BindView(R.id.text_card_place_info_name) TextView mTextViewPlaceName;
        @BindView(R.id.text_card_place_info_address) TextView mTextViewPlaceAddress;
        @BindView(R.id.rating_card_place_info) RatingBar mRatingBar;
        @BindView(R.id.text_card_place_info_rating) TextView mTextViewRating;
        @BindView(R.id.progress_card_place_image) ProgressBar mProgressBar;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_place_info, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Result place = mPlacesList.get(position);

        holder.mTextViewPlaceName.setText(place.getName());
        holder.mTextViewPlaceAddress.setText(place.getFormattedAddress());
        if (place.getRating() != null) {
            holder.mTextViewRating.setText(place.getRating().toString());
            holder.mTextViewRating.setTextColor(mContext.getResources().getColor(R.color.accent_material_light));
            holder.mRatingBar.setRating(place.getRating().floatValue());
        }

        // Load place image if it exists
        // TODO убрать комментарий
        /*
        if (place.getPhotos() != null) {
            int maxWidth = holder.mImageViewPlacePhoto.getMaxWidth();
            final String url = "https://maps.googleapis.com/maps/api/place/photo?"
                    + "maxwidth=" + maxWidth
                    + "&photoreference=" + place.getPhotos().get(0).getPhotoReference()
                    + "&key=" + API_KEY;

            Glide.with(mContext)
                    .load(url)
                    .transition(new DrawableTransitionOptions().crossFade(300))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.mProgressBar.setVisibility(View.GONE);
                            holder.mImageViewPlacePhoto.setImageResource(R.drawable.no_image);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.mProgressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.mImageViewPlacePhoto);
        } else {
            holder.mProgressBar.setVisibility(View.GONE);
            holder.mImageViewPlacePhoto.setImageResource(R.drawable.no_image);
        }
        */

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent placeInfoIntent = new Intent(mContext, PlaceInfoActivity.class);
                placeInfoIntent.putExtra(EXTRA_PLACE_ID, place.getPlaceId());
                mContext.startActivity(placeInfoIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlacesList.size();
    }
}
