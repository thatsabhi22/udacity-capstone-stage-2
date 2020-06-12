package com.udacity.spacebinge;

import com.udacity.spacebinge.models.Collection;
import com.udacity.spacebinge.models.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

//    @GET("/todos")
//    Call<List<Todo>> getTodos();
//
//    @GET("/todos/{id}")
//    Call<Todo> getTodo(@Path("id") int id);

    @GET("/search")
    Call<Result> getSpaceQuery(@Query("q") String query, @Query("media_type") String mediatype);

//    @POST("/todos")
//    Call<Todo> postTodo(@Body Todo todo);

}
