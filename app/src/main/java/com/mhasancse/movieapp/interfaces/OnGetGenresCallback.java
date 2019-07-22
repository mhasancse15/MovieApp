package com.mhasancse.movieapp.interfaces;

import com.mhasancse.movieapp.model.Genre;

import java.util.List;

public interface OnGetGenresCallback {
    void onSuccess(List<Genre> genres);

    void onError();
}
