package com.bca.popularmovie.holder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bca.popularmovie.R;

public class TrailerViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    View parent;


    public TrailerViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
        parent = itemView;
    }

    public void setData(String trailers) {
        textView.setText(trailers.trim());
    }

    public View getView() {
        return parent;
    }

}
