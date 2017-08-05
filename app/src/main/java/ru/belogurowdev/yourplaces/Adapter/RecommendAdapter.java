package ru.belogurowdev.yourplaces.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ru.belogurowdev.yourplaces.Model.Recommendation;
import ru.belogurowdev.yourplaces.R;

/**
 * Created by alexbelogurow on 05.08.17.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private Context mContext;
    private List<Recommendation> mRecommendationList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public RecommendAdapter(Context context, List<Recommendation> recommendationList) {
        mContext = context;
        mRecommendationList = recommendationList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_in_recommendations, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mRecommendationList.size();
    }


}
