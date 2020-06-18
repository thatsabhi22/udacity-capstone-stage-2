package com.udacity.spacebinge.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.udacity.spacebinge.R;
import com.udacity.spacebinge.repositories.VideoItemRepository;
import com.udacity.spacebinge.tasks.SpaceWebService;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    VideoItemRepository videoItemRepository;
    private SpaceWebService spaceWebService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spaceWebService = SpaceWebService.retrofit.create(SpaceWebService.class);

        videoItemRepository = VideoItemRepository.getInstance(this);
        videoItemRepository.getVideoCollection("latest", "video");
    }
}
