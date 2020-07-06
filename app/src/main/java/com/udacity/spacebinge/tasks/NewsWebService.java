package com.udacity.spacebinge.tasks;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.udacity.spacebinge.models.News;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NewsWebService {
    /**
     * URL for news data from the News API
     */
    String BASE_REQUEST_URL = "https://newsapi.org/v2";

    OkHttpClient.Builder okhttpclientbuilder = new OkHttpClient.Builder();

    HttpLoggingInterceptor httplogger = new HttpLoggingInterceptor();

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_REQUEST_URL)
            .client(okhttpclientbuilder
                    .addInterceptor(httplogger.setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(new StethoInterceptor())
                    .build())
            .build();

    @Headers("Content-Type: text/html")
    @GET("/everything")
    Call<News> getNewsData(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );
}
