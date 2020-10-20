package com.bca.popularmovie.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bca.popularmovie.R;
import com.bca.popularmovie.delegate.GeneralCallback;
import com.bca.popularmovie.holder.TrailerViewHolder;
import com.bca.popularmovie.model.Trailer;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter {

    private GeneralCallback callback = null;
    private List<Trailer> arrTrailers = new ArrayList<>();

    public void setDataTrailers(List<Trailer> arrString){
        arrTrailers = arrString;
    }

    public void setCallback(GeneralCallback callbackDelegate){
        this.callback = callbackDelegate;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder tmpHolder;
        View tmpView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_detail, parent, false);
        tmpHolder = new TrailerViewHolder(tmpView);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Trailer trailer = arrTrailers.get(position);
        ((TrailerViewHolder)holder).setData(trailer.getName());
        ((TrailerViewHolder)holder).getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.trailerPressed(trailer.getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrTrailers.size();
    }

}
