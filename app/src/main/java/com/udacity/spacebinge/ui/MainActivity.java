package com.udacity.spacebinge.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.udacity.spacebinge.ApiClient;
import com.udacity.spacebinge.R;
import com.udacity.spacebinge.models.Result;
import com.udacity.spacebinge.tasks.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    public static final String TAG = MainActivity.class.getSimpleName();

    Result aliens;
    Result space;
    Result mars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        getCollection("alien",aliens);
        getCollection("space", space);
        getCollection("mars", mars);
    }

    public void getCollection(String query, Result result){

        Call<Result> call = apiInterface.getSpaceQuery(query,"video");

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.e(TAG, "onResponse: " + response.body());

                Result result1 = response.body();
                setData(result1,result);
//                Log.e(TAG, "onResponse: " + queryResult.getCollection());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    void setData(Result result1,Result result){
        result = result1;
    }
}
