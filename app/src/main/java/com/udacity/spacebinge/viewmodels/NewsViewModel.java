package com.udacity.spacebinge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.udacity.spacebinge.models.News;
import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.repositories.NewsRepository;
import com.udacity.spacebinge.repositories.VideoItemRepository;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository newsRepository;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = NewsRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<News>> getAllWatchListItems(String query, String api_key) {
        return newsRepository.getLatestNews(query,api_key);
    }
}
