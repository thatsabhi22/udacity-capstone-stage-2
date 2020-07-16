package com.udacity.spacebinge.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.udacity.spacebinge.models.Article;
import com.udacity.spacebinge.models.News;
import com.udacity.spacebinge.tasks.NewsWebService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private NewsWebService newsWebService;

    public NewsRepository(Context context) {
        this.newsWebService = NewsWebService.retrofit.create(NewsWebService.class);
    }

    public static NewsRepository getInstance(Context context) {
        return new NewsRepository(context);
    }

    public LiveData<List<Article>> getLatestNews(String query, String api_key) {
        final MutableLiveData<List<Article>> data = new MutableLiveData<>();
        Call<News> call = newsWebService.getNewsData(query, api_key);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News newsDataResponse = response.body();
                    if (newsDataResponse != null) {
                        List<Article> list = newsDataResponse.getArticles();
                        data.setValue(list);
                    }
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d("Tangho", "Failure happened fetching space news");
            }
        });
        return data;
    }

    public List<Article> getNews(String query, String api_key) {
        final List<Article> data = new ArrayList<>();
        Call<News> call = newsWebService.getNewsData(query, api_key);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News newsDataResponse = response.body();
                    if (newsDataResponse != null) {
                        data.addAll(newsDataResponse.getArticles());
                    }
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d("Tangho", "Failure happened fetching space news");
            }
        });
        return data;
    }
}
