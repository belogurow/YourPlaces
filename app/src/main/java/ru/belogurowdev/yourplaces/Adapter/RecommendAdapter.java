package ru.belogurowdev.yourplaces.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.Model.Recommendation;
import ru.belogurowdev.yourplaces.R;

/**
 * Created by alexbelogurow on 05.08.17.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private Context mContext;
    private Recommendation mRecommendation;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView_card_tittle) TextView mTextViewCardTitle;

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
                .inflate(R.layout.card_in_recommendations, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String cardTitle = mRecommendation.getCardTitle(position);
        holder.mTextViewCardTitle.setText(cardTitle);
    }

    @Override
    public int getItemCount() {
        return mRecommendation.getSize();
    }


}
