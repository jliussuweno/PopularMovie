package com.bca.popularmovie.holder;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bca.popularmovie.R;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    View parent;


    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
        parent = itemView;
    }

    public void setData(String author, String content) {
        textView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        textView.setText("Review: " + content.trim() + "\n\nAuthor: " + author.trim());
    }

    public View getView() {
        return parent;
    }

}
