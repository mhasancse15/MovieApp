package com.mhasancse.movieapp.api;

import com.mhasancse.movieapp.model.Constans;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit=null;
    public  static Retrofit getInstans(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(Constans.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
