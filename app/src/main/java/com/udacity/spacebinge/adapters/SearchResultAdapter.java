package com.udacity.spacebinge.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.spacebinge.R;
import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.ui.PlayerActivity;

import java.util.List;

import static com.udacity.spacebinge.utils.ConstantUtil.INTENT_KEY_SOURCE_ACTIVITY;
import static com.udacity.spacebinge.utils.ConstantUtil.VIDEO_ITEM_OBJECT;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {

    Context mContext;
    List<VideoItem> mVideoItemsList;

    public SearchResultAdapter(Context context, List<VideoItem> videoItemsList) {
        this.mVideoItemsList = videoItemsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public SearchResultAdapter.SearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_search_result, null);
        SearchResultHolder rcv = new SearchResultHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.SearchResultHolder holder, int position) {
        final VideoItem current = mVideoItemsList.get(position);
        String url = mVideoItemsList.get(position).getThumbnail_url();
        if (!TextUtils.isEmpty(url)) {
            Picasso
                    .get()
                    .load(mVideoItemsList.get(position).getThumbnail_url())
                    .into(holder.search_result_thumbnail_iv);
        } else {
            holder.search_result_thumbnail_iv.setImageDrawable(null);
        }

        holder.search_result_title_tv.setText(mVideoItemsList.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra(INTENT_KEY_SOURCE_ACTIVITY, "search");
                intent.putExtra(VIDEO_ITEM_OBJECT, current);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoItemsList == null ? 0 : mVideoItemsList.size();
    }

    public class SearchResultHolder extends RecyclerView.ViewHolder {

        ImageView search_result_thumbnail_iv;
        TextView search_result_title_tv;

        public SearchResultHolder(@NonNull View itemView) {
            super(itemView);
            search_result_thumbnail_iv = itemView.findViewById(R.id.search_result_thumbnail_image_view);
            search_result_title_tv = itemView.findViewById(R.id.search_result_title_tv);
        }
    }
}
