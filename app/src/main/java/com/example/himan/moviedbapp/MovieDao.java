package com.example.himan.moviedbapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by himan on 25-Mar-18.
 */
@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieElement movie);
    @Insert
    void insert(ArrayList<MovieElement>movie_list);
    @Delete
    void delete(MovieElement movie);
    @Update
    void Update(MovieElement movie);
//    @Query("SELECT * FROM Movie WHERE isLiked is true")
//    ArrayList<MovieElement> getAllMovies();
}
