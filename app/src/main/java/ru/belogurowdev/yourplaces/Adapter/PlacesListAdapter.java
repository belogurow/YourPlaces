package ru.belogurowdev.yourplaces.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.Api.GooglePlacesModel.Result;
import ru.belogurowdev.yourplaces.R;

/**
 * Created by alexbelogurow on 11.08.17.
 */

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.ViewHolder> {
    private Context mContext;
    private List<Result> mPlacesList;

    public PlacesListAdapter(Context context,@Nullable List<Result> placesList) {
        mContext = context;
        mPlacesList = placesList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_place_info) CardView mCardView;
        @BindView(R.id.imageView_card_place_info_photo) ImageView mImageViewPlacePhoto;
        @BindView(R.id.textView_card_place_info_name) TextView mTextViewPlaceName;
        @BindView(R.id.textView_card_place_info_address) TextView mTextViewPlaceAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_place_info, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Result place = mPlacesList.get(position);

        holder.mTextViewPlaceName.setText(place.getName());
        holder.mTextViewPlaceAddress.setText(place.getFormattedAddress());
    }

    @Override
    public int getItemCount() {
        Log.d("places2", mPlacesList.size() + "");
        return mPlacesList.size();
    }
}
