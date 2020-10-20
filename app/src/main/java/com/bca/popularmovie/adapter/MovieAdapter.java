package com.bca.popularmovie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bca.popularmovie.R;
import com.bca.popularmovie.delegate.MovieCallback;
import com.bca.popularmovie.holder.MovieViewHolder;
import com.bca.popularmovie.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter {

    private MovieCallback callback = null;
    private List<Movie> arrMovies = new ArrayList<>();

    public void setData(List<Movie> arrMovie){
        arrMovies = arrMovie;
    }

    public void setCallback(MovieCallback callbackDelegate){
        this.callback = callbackDelegate;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder tmpHolder;
        View tmpView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_movie_item, parent, false);
        tmpHolder = new MovieViewHolder(tmpView);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = arrMovies.get(position);

        ((MovieViewHolder)holder).setData(movie.getImagePath(),movie.getTitle());
        ((MovieViewHolder)holder).getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.itemPressed(movie);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrMovies.size();
    }
}
