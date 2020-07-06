package com.udacity.spacebinge.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.spacebinge.R;
import com.udacity.spacebinge.models.Topic;
import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.ui.SearchActivity;
import com.udacity.spacebinge.utils.AppUtil;
import com.udacity.spacebinge.utils.HorizontalItemDecoration;
import com.udacity.spacebinge.utils.TransformUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.udacity.spacebinge.utils.ConstantUtil.INTENT_KEY_SOURCE_ACTIVITY;
import static com.udacity.spacebinge.utils.ConstantUtil.VIDEO_ITEM_SEARCH_KEY;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.TopicsViewHolder> {

    public ArrayList<Topic> topics;
    private Context context;
    private LayoutInflater layoutInflater;
    private LinkedHashMap<String, List<VideoItem>> videoCollection;
    private VideoItemsAdapter videoItemsAdapter;

    public HomePageAdapter(Context context, Map<String, List<VideoItem>> videoCollection) {
        this.context = context;
        this.videoCollection = (LinkedHashMap<String, List<VideoItem>>) videoCollection;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TopicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_topic_videos, parent, false);
        return new TopicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicsViewHolder holder, int position) {
        Map.Entry<String, List<VideoItem>> current = TransformUtils.getMapValueAt(videoCollection, position);
        holder.videoType.setText(current.getKey());
        holder.seeAlltextView.setText(context.getString(R.string.see_all_text));
        videoItemsAdapter = new VideoItemsAdapter(context, current.getValue());
        LinearLayoutManager horizontalManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        holder.horizontalRecyclerView.addItemDecoration(new HorizontalItemDecoration(AppUtil.dpToPx(context, 2), AppUtil.dpToPx(context, 2), AppUtil.dpToPx(context, 2)));
        holder.horizontalRecyclerView.setLayoutManager(horizontalManager);
        holder.horizontalRecyclerView.setAdapter(videoItemsAdapter);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchActivity.class);
                intent.putExtra(INTENT_KEY_SOURCE_ACTIVITY, "homepage");
                intent.putExtra(VIDEO_ITEM_SEARCH_KEY, current.getKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoCollection == null ? 0 : videoCollection.size();
    }

    public static class TopicsViewHolder extends RecyclerView.ViewHolder {
        TextView videoType;
        RecyclerView horizontalRecyclerView;
        TextView seeAlltextView;

        public TopicsViewHolder(View itemView) {
            super(itemView);
            videoType = itemView.findViewById(R.id.videoTypeTextView);
            seeAlltextView = itemView.findViewById(R.id.seeAllTextView);
            horizontalRecyclerView = itemView.findViewById(R.id.activityMainRecyclerViewHorizontal);
        }
    }
}
