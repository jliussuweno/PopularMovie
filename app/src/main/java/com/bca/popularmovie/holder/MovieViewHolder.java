package com.bca.popularmovie.holder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bca.popularmovie.R;
import com.squareup.picasso.Picasso;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

public class MovieViewHolder extends RecyclerView.ViewHolder{

    private ImageView iv_poster;
    private TextView tv_title;
    private View parent;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        iv_poster = itemView.findViewById(R.id.image_view_poster);
        tv_title = itemView.findViewById(R.id.text_view_title);
        parent = itemView;
    }

    public void setData(String imagePath, String title){
        Picasso.get().load("https://image.tmdb.org/t/p/w185" + imagePath).into(iv_poster);
        tv_title.setText(title.trim());
    }

    public View getView(){
        return parent;
    }

}
