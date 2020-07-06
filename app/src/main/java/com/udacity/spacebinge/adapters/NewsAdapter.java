package com.udacity.spacebinge.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.spacebinge.R;
import com.udacity.spacebinge.models.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    Context mContext;
    List<News> mNewsList;

    public NewsAdapter(Context context, List<News> newsList) {
        this.mNewsList = newsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        ImageView watch_list_thumbnail_iv;
        TextView watch_list_title_tv;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            watch_list_thumbnail_iv = itemView.findViewById(R.id.watch_list_thumbnail_image_view);
            watch_list_title_tv = itemView.findViewById(R.id.watch_list_title_tv);
        }
    }
}
