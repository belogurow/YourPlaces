package ru.belogurowdev.yourplaces.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.activities.PlaceInfoActivity;
import ru.belogurowdev.yourplaces.activities.PlacesListActivity;
import ru.belogurowdev.yourplaces.Api.GooglePlacesList.Result;
import ru.belogurowdev.yourplaces.R;

/**
 * Created by alexbelogurow on 11.08.17.
 */

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.ViewHolder> {

    private static final String TAG = PlacesListActivity.class.getSimpleName();
    private final static String API_KEY = "AIzaSyAuJIEnY4TcR-G67YJSgS2CNbPJNABzs3s";
    private static final String EXTRA_PLACE_ID = "ru.belogurowdev.extras.PLACE_ID";
    private static final String EXTRA_PLACE_PHOTO_REF = "ru.belogurowdev.extras.PLACE_PHOTO_REF";
    private RequestOptions mRequestOptions;

    private Context mContext;
    private List<Result> mPlacesList;

    public PlacesListAdapter(Context context,@Nullable List<Result> placesList) {
        mContext = context;
        mPlacesList = placesList;
        mRequestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
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
        if (place.getPhotos() != null) {
            int width = 720;
            String photoUrl = "https://maps.googleapis.com/maps/api/place/photo?"
                    + "maxwidth=" + width
                    + "&photoreference=" + place.getPhotos().get(0).getPhotoReference()
                    + "&key=" + API_KEY;
            Log.d(TAG, position + " " + photoUrl);


            Glide.with(mContext)
                    .load(photoUrl)
                    .transition(new DrawableTransitionOptions().crossFade(300))
                    .apply(mRequestOptions)
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


        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent placeInfoIntent = new Intent(mContext, PlaceInfoActivity.class);
                placeInfoIntent.putExtra(EXTRA_PLACE_ID, place.getPlaceId());
                if (place.getPhotos() != null) {
                    placeInfoIntent.putExtra(EXTRA_PLACE_PHOTO_REF, place.getPhotos().get(0).getPhotoReference());
                } else {
                    placeInfoIntent.putExtra(EXTRA_PLACE_PHOTO_REF, "");
                }
                mContext.startActivity(placeInfoIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlacesList.size();
    }
}
