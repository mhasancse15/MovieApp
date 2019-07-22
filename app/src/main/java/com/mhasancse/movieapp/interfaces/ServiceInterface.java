package com.mhasancse.movieapp.interfaces;

import com.mhasancse.movieapp.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceInterface {
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovie(@Query("api_key") String apiKey,@Query("page") int page);
}
