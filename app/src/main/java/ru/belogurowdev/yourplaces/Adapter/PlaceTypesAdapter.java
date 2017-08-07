package ru.belogurowdev.yourplaces.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.Model.PlaceType;
import ru.belogurowdev.yourplaces.R;

/**
 * Created by alexbelogurow on 06.08.17.
 */

public class PlaceTypesAdapter extends RecyclerView.Adapter<PlaceTypesAdapter.ViewHolder> {
    private Context mContext;
    private List<PlaceType> mPlaceTypes;


    public PlaceTypesAdapter(Context context, List<PlaceType> placeTypes) {
        mContext = context;
        mPlaceTypes = placeTypes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView_card_type) TextView mTextViewType;
        @BindView(R.id.imageView_card_type) ImageView mImageViewIcon;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PlaceType placeType = mPlaceTypes.get(position);

        holder.mTextViewType.setText(placeType.getType());
        holder.itemView.setBackground(mContext.getResources().getDrawable(placeType.getBackgroundImage()));
        //holder.mImageViewIcon.setImageDrawable(placeType.getBackground());
    }

    @Override
    public int getItemCount() {
        return mPlaceTypes.size();
    }
}
