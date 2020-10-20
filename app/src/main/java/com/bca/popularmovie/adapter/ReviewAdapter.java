package com.bca.popularmovie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bca.popularmovie.R;
import com.bca.popularmovie.delegate.MovieCallback;
import com.bca.popularmovie.holder.ReviewViewHolder;
import com.bca.popularmovie.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter {

    private List<Review> arrReviews = new ArrayList<>();
    public void setDataReview(List<Review> arrString) {
        arrReviews = arrString;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder tmpHolder;
        View tmpView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_detail, parent, false);
        tmpHolder = new ReviewViewHolder(tmpView);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Review review = arrReviews.get(position);
        ((ReviewViewHolder) holder).setData(review.getAuthor(), review.getContent());
    }

    @Override
    public int getItemCount() {
        return arrReviews.size();
    }
}
