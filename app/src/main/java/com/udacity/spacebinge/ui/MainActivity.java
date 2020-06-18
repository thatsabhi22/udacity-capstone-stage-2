package com.udacity.spacebinge.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.udacity.spacebinge.R;
import com.udacity.spacebinge.models.Result;
import com.udacity.spacebinge.repositories.VideoItemRepository;
import com.udacity.spacebinge.tasks.SpaceWebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private SpaceWebService spaceWebService;
    Result aliens;
    Result space;
    Result mars;
    VideoItemRepository videoItemRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spaceWebService = SpaceWebService.retrofit.create(SpaceWebService.class);

        videoItemRepository = VideoItemRepository.getInstance(this);
        videoItemRepository.getVideoCollection("latest","video");

//        getCollection("alien", aliens,getString(R.string.api_key));
//        getCollection("space", space,getString(R.string.api_key));
//        getCollection("mars", mars,getString(R.string.api_key));
    }

    public void getCollection(String query, Result result) {

        Call<Result> call = spaceWebService.getSpaceQuery(query, "video");

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.e(TAG, "onResponse: " + response.body());

                Result result1 = response.body();
                setData(result1, result);
//                Log.e(TAG, "onResponse: " + queryResult.getCollection());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    void setData(Result result1, Result result) {
        result = result1;
    }
}
