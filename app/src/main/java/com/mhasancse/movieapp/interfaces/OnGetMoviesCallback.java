package com.mhasancse.movieapp.interfaces;

import com.mhasancse.movieapp.model.Movie;

import java.util.List;

public interface OnGetMoviesCallback {

    void onSuccess(int page, List<Movie> movies);
    void onError();
}
