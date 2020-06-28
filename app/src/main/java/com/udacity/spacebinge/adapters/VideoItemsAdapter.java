package com.udacity.spacebinge.adapters;

import android.content.Context;
import android.content.Intent;
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

public class VideoItemsAdapter extends RecyclerView.Adapter<VideoItemsAdapter.VideoItemsViewHolder> {

    private Context mContext;
    private List<VideoItem> mVideoList;
    private LayoutInflater layoutInflater;

    public VideoItemsAdapter(Context context, List<VideoItem> videoList) {
        mVideoList = videoList;
        mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VideoItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_video_item, parent, false);
        return new VideoItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoItemsViewHolder holder, int position) {
        final VideoItem current = mVideoList.get(position);

        holder.title_tv.setText(current.getTitle());
        Picasso
                .get()
                .load(current.getThumbnail_url())
                .into(holder.thumbnail_iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PlayerActivity.class);
                intent.putExtra(INTENT_KEY_SOURCE_ACTIVITY,"homepage");
                intent.putExtra(VIDEO_ITEM_OBJECT,current);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mVideoList == null ? 0 : mVideoList.size();
    }

    public class VideoItemsViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail_iv;
        TextView title_tv;

        public VideoItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail_iv = itemView.findViewById(R.id.thumbnail_iv);
            title_tv = itemView.findViewById(R.id.title_tv);
        }
    }
}
