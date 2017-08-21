package ru.belogurowdev.yourplaces.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.R;

/**
 * Created by alexbelogurow on 14.08.17.
 */

public class PlaceInfoAdapter extends RecyclerView.Adapter<PlaceInfoAdapter.ViewHolder> {
    private Context mContext;
    private int textColor;
    private List<String> info;
    private List<Drawable> infoIcon;

    public PlaceInfoAdapter(Context context, List<String> info, List<Drawable> infoIcon) {
        mContext = context;
        this.info = info;
        this.infoIcon = infoIcon;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_info) TextView mTextViewInfo;
        @BindView(R.id.image_info_icon) ImageView mIconInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public PlaceInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_icon_info, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlaceInfoAdapter.ViewHolder holder, int position) {
        holder.mIconInfo.setImageDrawable(infoIcon.get(position));
        holder.mTextViewInfo.setTextColor(textColor);
        holder.mTextViewInfo.setText(info.get(position));
        //Toast.makeText(mContext, info.get(position), Toast.LENGTH_SHORT).show();
        //holder.mTextViewInfo.setTextColor(textColor);
    }

    @Override
    public int getItemCount() {
        return info.size();
    }
}
