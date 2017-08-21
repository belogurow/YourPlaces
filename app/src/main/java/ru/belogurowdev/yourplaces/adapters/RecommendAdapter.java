package ru.belogurowdev.yourplaces.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.activities.PlaceTypesActivity;
import ru.belogurowdev.yourplaces.models.Recommendation;
import ru.belogurowdev.yourplaces.R;

/**
 * Created by alexbelogurow on 05.08.17.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {
    private Context mContext;
    private Recommendation mRecommendation;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView_card_recommendation) TextView mTextViewCardTitle;
        @BindView(R.id.image_card_recommendation) ImageView mImageViewBackground;
        @BindView(R.id.card_recommendation) CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public RecommendAdapter(Context context, Recommendation recommendation) {
        this.mContext = context;
        this.mRecommendation = recommendation;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_recommendation, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String cardTitle = mRecommendation.getCardTitle(position);
        final Drawable cardImage = mRecommendation.getCardImage(position);

        holder.mTextViewCardTitle.setText(cardTitle);
        if (cardImage != null) {
            holder.mImageViewBackground.setBackground(cardImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleOne = mContext.getString(R.string.countries_for_tourism);
                if (mRecommendation.getRecommendationTitle().equals(titleOne)) {
                    Intent placeTypeIntent = new Intent(mContext, PlaceTypesActivity.class);
                    placeTypeIntent.putExtra("COUNTRY", mRecommendation.getCardTitle(position));
                    mContext.startActivity(placeTypeIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecommendation.getSize();
    }


}
