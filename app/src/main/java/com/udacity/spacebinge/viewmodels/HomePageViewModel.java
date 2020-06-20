package com.udacity.spacebinge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.repositories.VideoItemRepository;

import java.util.List;
import java.util.Map;

public class HomePageViewModel extends AndroidViewModel {

    private VideoItemRepository videoItemRepository;

    public HomePageViewModel(@NonNull Application application) {
        super(application);
        videoItemRepository = VideoItemRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Map<String,List<VideoItem>>>> getLatest(List<String> query, String media_type) {
        return videoItemRepository.getVideoCollection(query, media_type);
    }
}
