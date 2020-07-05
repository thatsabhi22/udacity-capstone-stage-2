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

public class DownloadedAdapter extends RecyclerView.Adapter<DownloadedAdapter.DownloadedHolder> {

    Context mContext;
    List<VideoItem> mVideoItemsList;

    public DownloadedAdapter(Context context, List<VideoItem> videoItemsList) {
        this.mVideoItemsList = videoItemsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public DownloadedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_downloaded_item, null);
        DownloadedHolder rcv = new DownloadedHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadedHolder holder, int position) {
        final VideoItem current = mVideoItemsList.get(position);

        String url = mVideoItemsList.get(position).getThumbnail_url();
        if (!TextUtils.isEmpty(url)) {
            Picasso
                    .get()
                    .load(mVideoItemsList.get(position).getThumbnail_url())
                    .into(holder.download_list_thumbnail_iv);
        } else {
            holder.download_list_thumbnail_iv.setImageDrawable(null);
        }

        holder.download_list_title_tv.setText(mVideoItemsList.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra(INTENT_KEY_SOURCE_ACTIVITY, "downloadlist");
                intent.putExtra(VIDEO_ITEM_OBJECT, current);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoItemsList == null ? 0 : mVideoItemsList.size();
    }

    public class DownloadedHolder extends RecyclerView.ViewHolder {
        ImageView download_list_thumbnail_iv;
        TextView download_list_title_tv;

        public DownloadedHolder(@NonNull View itemView) {
            super(itemView);
            download_list_thumbnail_iv = itemView.findViewById(R.id.download_list_thumbnail_iv);
            download_list_title_tv = itemView.findViewById(R.id.download_list_title_tv);
        }
    }
}
