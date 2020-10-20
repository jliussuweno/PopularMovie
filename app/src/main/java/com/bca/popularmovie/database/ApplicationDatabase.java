package com.bca.popularmovie.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.bca.popularmovie.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {

    private static ApplicationDatabase instance = null;
    public abstract MoviesDao moviesDao();

    public static ApplicationDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, ApplicationDatabase.class, "favorite").allowMainThreadQueries().build();
        }
        return instance;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
