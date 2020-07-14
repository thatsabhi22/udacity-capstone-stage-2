package com.udacity.spacebinge.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.spacebinge.R;
import com.udacity.spacebinge.models.Article;
import com.udacity.spacebinge.ui.WebActivity;
import com.udacity.spacebinge.utils.AppUtil;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    Context mContext;
    List<Article> mNewsList;

    public NewsAdapter(Context context, List<Article> newsList) {
        this.mNewsList = newsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_news_card, null);
        NewsHolder rcv = new NewsHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        final Article current = mNewsList.get(position);

        String imageUrl = current.getUrlToImage();
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso
                    .get()
                    .load(imageUrl)
                    .into(holder.news_item_iv);
        } else {
            holder.news_item_iv.setImageDrawable(null);
        }
        holder.news_title_tv.setText(current.getTitle());
        holder.news_author_tv.setText(current.getAuthor());
        holder.published_at_tv.setText(AppUtil.formatDate(current.getPublishedAt()));
        holder.news_url_tv.setText(current.getUrl());
        holder.news_description_tv.setText(current.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, current.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("newsArticleUrl", current.getUrl());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsList == null ? 0 : mNewsList.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        ImageView news_item_iv;
        TextView news_title_tv, news_author_tv, published_at_tv, news_url_tv, news_description_tv;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            news_item_iv = itemView.findViewById(R.id.news_item_iv);
            news_title_tv = itemView.findViewById(R.id.news_title_tv);
            news_author_tv = itemView.findViewById(R.id.news_author_tv);
            published_at_tv = itemView.findViewById(R.id.published_at_tv);
            news_url_tv = itemView.findViewById(R.id.news_url_tv);
            news_description_tv = itemView.findViewById(R.id.news_description_tv);
        }
    }
}
