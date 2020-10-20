package com.bca.popularmovie;

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
import com.bca.popularmovie.model.Review;
import com.bca.popularmovie.model.Trailer;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DescriptionViewModel extends AndroidViewModel {

    private static final String TAG = "JEJE";
    Context context;
    private MutableLiveData<List<Trailer>> dataTrailer;
    private MutableLiveData<List<Review>> dataReview;
    private LiveData<List<Movie>> dataFavorite;
    private MoviesDao mMovieDao;
    public Boolean isFavorite;

    public DescriptionViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        dataTrailer = new MutableLiveData<>();
        dataReview = new MutableLiveData<>();
        ApplicationDatabase db = ApplicationDatabase.getInstance(context);
        mMovieDao = db.moviesDao();
        dataFavorite = mMovieDao.selectMovies();
    }

    public LiveData<List<Trailer>> getDataTrailer(){
        return dataTrailer;
    }
    public LiveData<List<Review>> getDataReview(){
        return dataReview;
    }

    public void initDataTrailers(String idMovie){
        RequestQueue queue = Volley.newRequestQueue(context);

        String url ="https://api.themoviedb.org/3/movie/" + idMovie + "/videos?api_key=363a304bf3803692ae784f270971be88";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        List<Trailer> arrTrailers = new ArrayList<>();

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("results");
                            for(int i = 0; i < array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);

                                String id = jsonObject.getString("key");
                                String title = jsonObject.getString("name");
                                arrTrailers.add(new Trailer(id, title));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dataTrailer.postValue(arrTrailers);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void initDataReview(String idMovie){
        RequestQueue queue = Volley.newRequestQueue(context);

        String url ="https://api.themoviedb.org/3/movie/" + idMovie + "/reviews?api_key=363a304bf3803692ae784f270971be88";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Review> arrReview = new ArrayList<>();

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("results");
                            for(int i = 0; i < array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);

                                String author = jsonObject.getString("author");
                                String content = jsonObject.getString("content");
                                arrReview.add(new Review(author, content));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dataReview.postValue(arrReview);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void insertFavoriteMovies(Movie movie){
        mMovieDao.insertMovie(movie);
    }

    public void checkFavorite(Movie movie){

        int count = mMovieDao.checkMovies(movie.getId());
        if (count == 1){
            isFavorite = true;
        } else {
            isFavorite = false;
        }
    }

    public void deleteFavoriteMovies(Movie movie) {
        mMovieDao.deleteMovie(movie.getId());
    }
}
