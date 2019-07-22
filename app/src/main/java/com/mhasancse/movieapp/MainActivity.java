package com.mhasancse.movieapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mhasancse.movieapp.adapter.MoviesAdapter;
import com.mhasancse.movieapp.adapter.Pagination;
import com.mhasancse.movieapp.api.RetrofitClient;
import com.mhasancse.movieapp.interfaces.OnGetGenresCallback;
import com.mhasancse.movieapp.interfaces.OnGetMoviesCallback;
import com.mhasancse.movieapp.interfaces.ServiceInterface;
import com.mhasancse.movieapp.model.Constans;
import com.mhasancse.movieapp.model.Genre;
import com.mhasancse.movieapp.model.Movie;
import com.mhasancse.movieapp.model.MoviesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MoviesAdapter adapter;
    private ServiceInterface service;
    private ProgressBar progressBar;



    //Pagination
    private static final int START_PAGE=1;
    private int TOTAL_PAGE=20;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int CURRENT_PAGE=START_PAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Constans.API_KEY==null){
            Toast.makeText(this, "Not Found API Key", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar=findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.movies_list);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


         service= RetrofitClient.getInstans().create(ServiceInterface.class);
        recyclerView.addOnScrollListener(new Pagination(linearLayoutManager) {
            @Override
            protected void loadMoreItem() {
                isLoading=true;
                CURRENT_PAGE +=1;

                //LOADING PAGE
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                },100);

            }

            @Override
            protected int getTotalPage() {
                return TOTAL_PAGE;
            }

            @Override
            protected boolean lastPage() {
                return isLastPage;
            }

            @Override
            protected boolean isLoading() {
                return isLoading;
            }
        });
        Call<MoviesResponse>call=service.getPopularMovie(Constans.API_KEY,START_PAGE);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<Movie>movies=response.body().getMovies();
                adapter=new MoviesAdapter(movies,MainActivity.this);
                recyclerView.setAdapter(adapter);

                if(CURRENT_PAGE<=TOTAL_PAGE){
                    adapter.addBottemItem();
                }else {
                    isLastPage=true;
                }
                progressBar.setVisibility(View.GONE);


                Toast.makeText(MainActivity.this, "Movie Size"+movies.size(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });




    }

    private void loadNextPage() {
        progressBar.setVisibility(View.VISIBLE);
        Call<MoviesResponse>call=service.getPopularMovie(Constans.API_KEY,CURRENT_PAGE);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<Movie>movies=response.body().getMovies();

                //REMOVED LAST EMPTY
                adapter.removeLastEmptyItem();
                isLoading=false;
                adapter.addAll(movies);
                if(CURRENT_PAGE !=TOTAL_PAGE){
                    adapter.addBottemItem();
                }else {
                    isLastPage=true;
                }
                progressBar.setVisibility(View.GONE);



            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });
    }

}
