package com.udacity.spacebinge.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoItemsAdapter extends RecyclerView.Adapter<VideoItemsAdapter.VideoItemsViewHolder> {

    @NonNull
    @Override
    public VideoItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoItemsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class VideoItemsViewHolder extends RecyclerView.ViewHolder {

        public VideoItemsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
