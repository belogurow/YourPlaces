package ru.belogurowdev.yourplaces.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belogurowdev.yourplaces.Model.AndroidLibrary;
import ru.belogurowdev.yourplaces.R;

/**
 * Created by alexbelogurow on 14.08.17.
 */

public class AndroidLibraryAdapter extends RecyclerView.Adapter<AndroidLibraryAdapter.ViewHolder> {
    private Context mContext;
    private List<AndroidLibrary> mLibraries;

    public AndroidLibraryAdapter(Context context, List<AndroidLibrary> libraries) {
        mContext = context;
        mLibraries = libraries;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_library_title) TextView mTextViewTitle;
        @BindView(R.id.text_library_description) TextView mTextViewDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    @Override
    public AndroidLibraryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).
                inflate(R.layout.item_android_library, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AndroidLibraryAdapter.ViewHolder holder, int position) {
        final AndroidLibrary library = mLibraries.get(position);

        holder.mTextViewTitle.setText(library.getTitle());
        holder.mTextViewDescription.setText(library.getDescription());
    }

    @Override
    public int getItemCount() {
        return mLibraries.size();
    }
}
