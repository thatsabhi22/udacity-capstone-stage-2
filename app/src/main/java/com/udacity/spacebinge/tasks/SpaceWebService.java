package com.udacity.spacebinge.tasks;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.udacity.spacebinge.models.Result;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SpaceWebService {
    /**
     * URL for movies data from the MoviesDB data-set
     */
    String BASE_REQUEST_URL = "https://images-api.nasa.gov";

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
    @GET("/search")
    Call<Result> getSpaceQuery(
            @Query("q") String query,
            @Query("media_type") String mediatype
//            ,@Query("api_key") String apikey
    );
}
