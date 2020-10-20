package com.bca.popularmovie.delegate;

import com.bca.popularmovie.model.Movie;

import java.util.List;

public interface GeneralCallback {

    void itemPressed(Movie movies);
    void trailerPressed(String id);
}
