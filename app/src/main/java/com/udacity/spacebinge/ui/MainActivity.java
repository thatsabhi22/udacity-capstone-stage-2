package com.udacity.spacebinge.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.udacity.spacebinge.R;
import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.repositories.VideoItemRepository;
import com.udacity.spacebinge.tasks.SpaceWebService;
import com.udacity.spacebinge.viewmodels.HomePageViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    VideoItemRepository videoItemRepository;
    HomePageViewModel homePageViewModel;
    Observer<List<VideoItem>> videoItemObserver;
    private SpaceWebService spaceWebService;
    private List<VideoItem> videoItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spaceWebService = SpaceWebService.retrofit.create(SpaceWebService.class);

        videoItemRepository = VideoItemRepository.getInstance(this);
        videoItemRepository.getVideoCollection("latest", "video");

        initHomepageViewModel();
        videoItemList = new ArrayList<>();

    }

    private void initHomepageViewModel() {
        videoItemObserver =
                movies -> {
                    videoItemList.clear();
                    videoItemList.addAll(movies);
                };
        homePageViewModel = ViewModelProviders.of(this)
                .get(HomePageViewModel.class);
        homePageViewModel.getLatest("latest", "video")
                .observe(MainActivity.this, videoItemObserver);
    }
}
