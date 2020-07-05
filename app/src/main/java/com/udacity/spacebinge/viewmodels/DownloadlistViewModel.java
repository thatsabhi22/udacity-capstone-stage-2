package com.udacity.spacebinge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.repositories.VideoItemRepository;

import java.util.List;

public class DownloadlistViewModel extends AndroidViewModel {

    private VideoItemRepository videoItemRepository;

    public DownloadlistViewModel(@NonNull Application application) {
        super(application);
        videoItemRepository = VideoItemRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<VideoItem>> getAllDownloadedListItems() {
        return videoItemRepository.getAllDownloadedListItems();
    }
}



