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
 * Created by himan on 18-Apr-18.
 */
@Dao
public interface TVDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TVElement tvElement);
    @Insert
    void insert(ArrayList<TVElement>tvElements);
    @Delete
    void delete(TVElement tvElement);
    @Update
    void update(TVElement tvElement);
//    @Query("SELECT * FROM TV WHERE isLiked is true")
//    ArrayList<TVElement>getTVList();
}
