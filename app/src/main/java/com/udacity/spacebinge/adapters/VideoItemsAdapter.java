package com.udacity.spacebinge.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;
import com.udacity.spacebinge.R;
import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.ui.HomeActivity;
import com.udacity.spacebinge.ui.PlayerActivity;
import com.udacity.spacebinge.utils.AppUtil;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
        Glide
                .with(mContext)
                .asGif().load(R.drawable.globe_loading)
                .into(holder.placeholder_iv);

        Glide.with(mContext)
                .load(current.getThumbnail_url())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.placeholder_iv.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.placeholder_iv.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.thumbnail_iv);

        Picasso
                .get()
                .load(current.getThumbnail_url())
                .into(holder.thumbnail_iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                boolean isOffline = false;
                try {
                    isOffline = new AppUtil.CheckOnlineStatus().execute().get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                if (isOffline) {
                    intent = new Intent(mContext, HomeActivity.class);
                } else {
                    intent = new Intent(mContext, PlayerActivity.class);
                }

                intent.putExtra(INTENT_KEY_SOURCE_ACTIVITY, "homepage");
                intent.putExtra(VIDEO_ITEM_OBJECT, current);
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
        ImageView placeholder_iv;
        TextView title_tv;

        public VideoItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail_iv = itemView.findViewById(R.id.thumbnail_iv);
            title_tv = itemView.findViewById(R.id.title_tv);
            placeholder_iv = itemView.findViewById(R.id.loading_placeholder_iv);
        }
    }
}
