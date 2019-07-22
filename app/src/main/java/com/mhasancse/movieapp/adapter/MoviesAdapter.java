package com.mhasancse.movieapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mhasancse.movieapp.R;
import com.mhasancse.movieapp.model.Genre;
import com.mhasancse.movieapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private Context context;

    public MoviesAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Glide.with(context).load(movieList.get(position).getBackdropPath()).into(holder.poster);
        holder.title.setText(movieList.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView poster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_movie_title);
            poster = itemView.findViewById(R.id.item_movie_poster);

        }

    }

    public void add(Movie movie) {
        movieList.add(movie);
        notifyItemChanged(movieList.size() - 1);
    }
    public void addAll(List<Movie> movies){
        for(Movie m:movies){
            add(m);
        }
    }

    //Add empty item
    public void addBottemItem(){
        add(new Movie());
    }

    //REMOVED LAST empty item
    public void removeLastEmptyItem(){
        int position=movieList.size()-1;
        Movie item=getItem(position);
        if(item !=null){
            movieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Movie getItem(int position) {
        return movieList.get(position);
    }

}
