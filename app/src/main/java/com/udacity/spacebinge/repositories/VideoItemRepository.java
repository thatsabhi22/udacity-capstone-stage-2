package com.udacity.spacebinge.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.udacity.spacebinge.database.VideoItemsDatabase;
import com.udacity.spacebinge.models.Result;
import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.tasks.SpaceWebService;
import com.udacity.spacebinge.utils.TransformUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoItemRepository {

    private SpaceWebService spaceWebService;
    private VideoItemsDatabase mDatabase;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    private VideoItemRepository(Context context) {
        spaceWebService = SpaceWebService.retrofit.create(SpaceWebService.class);
        mDatabase = VideoItemsDatabase.getInstance(context);
    }

    public static VideoItemRepository getInstance(Context context) {
        return new VideoItemRepository(context);
    }

    public LiveData<List<Map<String, List<VideoItem>>>> getVideoCollection(List<String> queries, String media_type) {
        final MutableLiveData<List<Map<String, List<VideoItem>>>> data = new MutableLiveData<>();
        List<Map<String, List<VideoItem>>> videoCollection = new ArrayList<>();
        for (String query : queries) {
            Call<Result> call = spaceWebService.getSpaceQuery(query, media_type);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()) {
                        Result movieTrailerResponse = response.body();
                        if (movieTrailerResponse != null) {
                            List<VideoItem> list = TransformUtils.extractVideoItemFromResult(movieTrailerResponse);
                            Map<String, List<VideoItem>> as = new HashMap<>();
                            as.put(query, list);
                            videoCollection.add(as);
                            data.setValue(videoCollection);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.d("Tangho", "Failure happened fetching space videos");
                }
            });
        }
        return data;
    }
}

