package com.bca.popularmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bca.popularmovie.adapter.MovieAdapter;
import com.bca.popularmovie.delegate.GeneralCallback;
import com.bca.popularmovie.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GeneralCallback, Serializable {

    private static final String TAG = "JEJE";
    private MovieAdapter adapter = new MovieAdapter();
    MainViewModel mainViewModel;
    RecyclerView obj_recyclerview;
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
        mainViewModel.initDataFavorite();
        mainViewModel.getDataFavorit().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.setData(movies);
                adapter.notifyDataSetChanged();
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
        switch (item.getItemId()) {
            case R.id.popular_item:
                item.setTitle("Popular Movie");
                initDataPopular();
                return true;
            case R.id.top_rated_item:
                initDataTopRated();
                item.setTitle("Top Rated Movie");
                return true;
            case R.id.favorite:
                initDataFavorite();
                item.setTitle("Favorite Movie");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void itemPressed(Movie movies) {
        Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
        intent.putExtra("movie", (Serializable) movies);
        startActivity(intent);
    }

    @Override
    public void trailerPressed(String id) {

    }
}