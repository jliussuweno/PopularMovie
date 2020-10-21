package com.bca.popularmovie.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bca.popularmovie.viewmodel.DescriptionViewModel;
import com.bca.popularmovie.R;
import com.bca.popularmovie.adapter.ReviewAdapter;
import com.bca.popularmovie.adapter.TrailerAdapter;
import com.bca.popularmovie.delegate.TrailerCallback;
import com.bca.popularmovie.model.Movie;
import com.bca.popularmovie.model.Review;
import com.bca.popularmovie.model.Trailer;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

public class DescriptionActivity extends AppCompatActivity implements TrailerCallback, Serializable {

    private static final String TAG = "JEJE";
    TextView tv_title, tv_releasedate, tv_rating, tv_description;
    ImageView iv_movie;
    RecyclerView rv_trailers, rv_reviews;
    TrailerAdapter trailerAdapter;
    ReviewAdapter reviewAdapter;
    ToggleButton favorite_button;
    private final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private final LinearLayoutManager linearLayoutManagerReview = new LinearLayoutManager(this);
    DescriptionViewModel descriptionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        tv_title = findViewById(R.id.tv_title);
        tv_releasedate = findViewById(R.id.textView_releaseDate);
        tv_rating = findViewById(R.id.textView_rating);
        tv_description = findViewById(R.id.textView_description);
        iv_movie = findViewById(R.id.imageView_movie);
        rv_trailers = findViewById(R.id.trailers_rv);
        rv_reviews = findViewById(R.id.review_rv);
        favorite_button = findViewById(R.id.toggleButton);

        rv_reviews.setHasFixedSize(true);
        rv_reviews.setLayoutManager(linearLayoutManager);

        rv_trailers.setHasFixedSize(true);
        rv_trailers.setLayoutManager(linearLayoutManagerReview);

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("movie");
        String titleBar = intent.getStringExtra("titleBar");
        String id = movie.getId();
        tv_title.setText(movie.getTitle());
        tv_releasedate.setText(movie.getReleaseDate());
        tv_rating.setText(movie.getRating() + " / 10");
        tv_description.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        tv_description.setText(movie.getDescription());
        Picasso.get().load("https://image.tmdb.org/t/p/w185" + movie.getImagePath()).into(iv_movie);

        trailerAdapter = new TrailerAdapter();
        trailerAdapter.setCallback(this);
        rv_trailers.setAdapter(trailerAdapter);

        reviewAdapter = new ReviewAdapter();
        rv_reviews.setAdapter(reviewAdapter);

//        getActionBar().setTitle(titleBar);

        descriptionViewModel = new ViewModelProvider(this).get(DescriptionViewModel.class);
        descriptionViewModel.initDataTrailers(id);
        descriptionViewModel.getDataTrailer().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailerAdapter.setDataTrailers(trailers);
                trailerAdapter.notifyDataSetChanged();
            }
        });

        descriptionViewModel.initDataReview(id);
        descriptionViewModel.getDataReview().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setDataReview(reviews);
                reviewAdapter.notifyDataSetChanged();
            }
        });

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        descriptionViewModel.checkFavorite(movie);
        favorite_button.setChecked(descriptionViewModel.isFavorite);
        favorite_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.startAnimation(scaleAnimation);
                if (isChecked == true){
                    descriptionViewModel.insertFavoriteMovies(movie);
                } else {
                    descriptionViewModel.deleteFavoriteMovies(movie);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void trailerPressed(String id) {
        String url = "http://www.youtube.com/watch?v=" + id;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}