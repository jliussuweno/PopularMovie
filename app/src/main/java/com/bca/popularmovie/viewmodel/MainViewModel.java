package com.bca.popularmovie.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bca.popularmovie.database.ApplicationDatabase;
import com.bca.popularmovie.database.MoviesDao;
import com.bca.popularmovie.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel implements Serializable {

    Context context;
    private MutableLiveData<List<Movie>> dataTop;
    private MutableLiveData<List<Movie>> dataPopular;
    private LiveData<List<Movie>> dataFavorite;
    private MoviesDao mMovieDao;
    private static final String TAG = "JEJE";

    public MainViewModel(@NonNull Application application) {
        super(application);
        dataTop = new MutableLiveData<>();
        dataPopular = new MutableLiveData<>();
        context = application.getApplicationContext();
        ApplicationDatabase db = ApplicationDatabase.getInstance(context);
        mMovieDao = db.moviesDao();
        initDataPopular();
    }

    public LiveData<List<Movie>> getDataTop() {
        return dataTop;
    }

    public LiveData<List<Movie>> getDataPopular() {
        return dataPopular;
    }

    public LiveData<List<Movie>> getDataFavorit(){
        return dataFavorite;
    }

    public void initDataPopular(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://api.themoviedb.org/3/movie/popular?api_key=363a304bf3803692ae784f270971be88";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    List<Movie> arrMovies = new ArrayList<>();

                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray array = object.getJSONArray("results");
                        for(int i = 0; i < array.length(); i++){
                            JSONObject jsonObject = array.getJSONObject(i);

                            String id = jsonObject.getString("id");
                            String title = jsonObject.getString("title");
                            String releaseDate = jsonObject.getString("release_date");
                            String description = jsonObject.getString("overview");
                            String posterPath = jsonObject.getString("poster_path");
                            String voteAverage = jsonObject.getString("vote_average");
                            arrMovies.add(new Movie(id,title,posterPath,releaseDate,voteAverage,description));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dataPopular.postValue(arrMovies);

                }, error -> Log.d(TAG,"That didn't work!"));

        queue.add(stringRequest);
    }

    public void initDataTopRated(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://api.themoviedb.org/3/movie/top_rated?api_key=363a304bf3803692ae784f270971be88";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Movie> arrMovies = new ArrayList<>();

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("results");
                            for(int i = 0; i < array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String title = jsonObject.getString("title");
                                String releaseDate = jsonObject.getString("release_date");
                                String description = jsonObject.getString("overview");
                                String posterPath = jsonObject.getString("poster_path");
                                String voteAverage = jsonObject.getString("vote_average");
                                arrMovies.add(new Movie(id,title,posterPath,releaseDate,voteAverage,description));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dataTop.postValue(arrMovies);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void initDataFavorite(){
        dataFavorite = mMovieDao.selectMovies();
    }
}
