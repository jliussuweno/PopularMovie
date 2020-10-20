package com.bca.popularmovie.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.bca.popularmovie.model.Movie;

import java.util.List;

@Dao
public interface MoviesDao {

    @Insert
    void insertMovie(Movie entityMovie);

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> selectMovies();

    @Query("DELETE FROM movies where id = :id")
    void deleteMovie(String id);

    @Query("SELECT COUNT(*) FROM movies where id= :id")
    int checkMovies(String id);


}
