package com.udacity.spacebinge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.repositories.VideoItemRepository;

public class PlayerViewModel extends AndroidViewModel {

    private VideoItemRepository videoItemRepository;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        videoItemRepository = VideoItemRepository.getInstance(application.getApplicationContext());
    }

    public void addVideoToWatchlist(VideoItem videoItem) {
        videoItemRepository.addVideoToWatchlist(videoItem);
    }

    public void deleteVideoToWatchlist(String videoItem) {
        videoItemRepository.deleteVideoToWatchlist(videoItem);
    }

    public LiveData<VideoItem> getVideoItemsByNasaIdWatchList(String nasaId) {
        return videoItemRepository.getVideoItemsByNasaIdWatchList(nasaId);
    }

    public LiveData<VideoItem> getVideoItemsByNasaIdDownloadList(String nasaId) {
        return videoItemRepository.getVideoItemsByNasaIdDownloadList(nasaId);
    }

    public VideoItem isVideoItemPresentInList(String nasaId, String list) {
        return videoItemRepository.isVideoItemPresentInList(nasaId, list);
    }
}
