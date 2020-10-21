package com.bca.popularmovie.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.bca.popularmovie.viewmodel.MainViewModel;
import com.bca.popularmovie.R;
import com.bca.popularmovie.adapter.MovieAdapter;
import com.bca.popularmovie.delegate.MovieCallback;
import com.bca.popularmovie.model.Movie;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieCallback, Serializable {

    private static final String TAG = "JEJE";
    private MovieAdapter adapter = new MovieAdapter();
    MainViewModel mainViewModel;
    RecyclerView obj_recyclerview;
    Boolean favoriteFlag = false;
    String stateMovie = "Popular Movie";
    private final GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        obj_recyclerview = findViewById(R.id.obj_recyclerview);
        obj_recyclerview.setHasFixedSize(true);
        obj_recyclerview.setLayoutManager(gridLayoutManager);
        adapter.setCallback(this);
        obj_recyclerview.setAdapter(adapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        initDataPopular();
    }

    private void initDataPopular(){
        favoriteFlag = false;
        stateMovie = "Popular Movie";
        mainViewModel.initDataPopular();
        mainViewModel.getDataPopular().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.setData(movies);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void  initDataTopRated(){
        favoriteFlag = false;
        stateMovie = "Top Rated Movie";
        mainViewModel.initDataTopRated();
        mainViewModel.getDataTop().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.setData(movies);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void  initDataFavorite(){
        favoriteFlag = true;
        stateMovie = "Favorite Movie";
        mainViewModel.initDataFavorite();
        mainViewModel.getDataFavorit().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (favoriteFlag){
                    adapter.setData(movies);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String titleBar = "";
        switch (item.getItemId()) {
            case R.id.popular_item:
                initDataPopular();
                item.setTitle("Popular Movie");
                getSupportActionBar().setTitle("Popular Movie");
                return true;
            case R.id.top_rated_item:
                initDataTopRated();
                item.setTitle("Top Rated Movie");
                getSupportActionBar().setTitle("Top Rated Movie");
                return true;
            case R.id.favorite:
                initDataFavorite();
                item.setTitle("Favorite Movie");
                getSupportActionBar().setTitle("Favorite Movie");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void itemPressed(Movie movies) {
        Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
        intent.putExtra("movie", (Serializable) movies);
        intent.putExtra("titleBar", stateMovie);
        startActivity(intent);
    }
}