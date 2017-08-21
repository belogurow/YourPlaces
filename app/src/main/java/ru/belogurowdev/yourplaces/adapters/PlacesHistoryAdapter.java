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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import ru.belogurowdev.yourplaces.activities.PlaceInfoActivity;
import ru.belogurowdev.yourplaces.models.PlaceRealm;
import ru.belogurowdev.yourplaces.R;

/**
 * Created by alexbelogurow on 16.08.17.
 */

public class PlacesHistoryAdapter extends RecyclerView.Adapter<PlacesHistoryAdapter.ViewHolder> {

    private static final String TAG = PlacesHistoryAdapter.class.getSimpleName();
    private final static String API_KEY = "AIzaSyAuJIEnY4TcR-G67YJSgS2CNbPJNABzs3s";
    private static final String EXTRA_PLACE_ID = "ru.belogurowdev.extras.PLACE_ID";
    private static final String EXTRA_PLACE_PHOTO_URL = "ru.belogurowdev.extras.PLACE_PHOTO_URL";
    private RequestOptions mRequestOptions;

    private Context mContext;
    private RealmResults<PlaceRealm> mPlacesList;

    public PlacesHistoryAdapter(Context context,@Nullable RealmResults<PlaceRealm> placesList) {
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
        final PlaceRealm place = mPlacesList.get(position);

        holder.mTextViewPlaceName.setText(place.getPlaceName());
        holder.mTextViewPlaceAddress.setText(place.getPlaceAddress());
        if (place.getPlaceRating() != 0.0) {
            holder.mTextViewRating.setText(String.valueOf(place.getPlaceRating()));
            holder.mTextViewRating.setTextColor(mContext.getResources().getColor(R.color.accent_material_light));
            holder.mRatingBar.setRating((float) place.getPlaceRating());
        }

        // Load place image if it exists
        if (place.getPhotoReference().length() > 0) {
            int width = 720;
            String photoUrl = "https://maps.googleapis.com/maps/api/place/photo?"
                    + "maxwidth=" + width
                    + "&photoreference=" + place.getPhotoReference()
                    + "&key=" + API_KEY;
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
        Log.d(TAG, "1 " + place.toString());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent placeInfoIntent = new Intent(mContext, PlaceInfoActivity.class);
                placeInfoIntent.putExtra(EXTRA_PLACE_ID, place.getPlaceId());
                Log.d(TAG, "2 " + place.toString());
                placeInfoIntent.putExtra(EXTRA_PLACE_PHOTO_URL, place.getPhotoReference());
                mContext.startActivity(placeInfoIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlacesList.size();
    }
}
