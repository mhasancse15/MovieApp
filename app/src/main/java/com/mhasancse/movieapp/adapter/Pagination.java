package com.mhasancse.movieapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class Pagination extends RecyclerView.OnScrollListener {
    LinearLayoutManager linearLayoutManager;


    public Pagination(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItem=linearLayoutManager.getChildCount();
        int totalItemCount=linearLayoutManager.getItemCount();
        int firstVisibleItemPosition=linearLayoutManager.findFirstVisibleItemPosition();
        
        if( !isLoading() && !lastPage()){
            if((visibleItem+firstVisibleItemPosition)>= totalItemCount &&
                    firstVisibleItemPosition>=0 && totalItemCount>=getTotalPage()){
                loadMoreItem();
                
            }
            
        }
    }

    protected abstract void loadMoreItem();

    protected abstract int getTotalPage();

    protected abstract boolean lastPage();

    protected abstract boolean isLoading();

}
