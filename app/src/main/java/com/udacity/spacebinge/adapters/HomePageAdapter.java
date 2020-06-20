package com.udacity.spacebinge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.spacebinge.R;
import com.udacity.spacebinge.models.Topic;
import com.udacity.spacebinge.models.VideoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.TopicsViewHolder> {

    public ArrayList<Topic> topics;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Map<String, List<VideoItem>>> videoCollection;

    public HomePageAdapter(Context context, List<Map<String, List<VideoItem>>> videoCollection) {
        this.context = context;
        this.videoCollection = videoCollection;
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

    }

    @Override
    public int getItemCount() {
        return 0;
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
