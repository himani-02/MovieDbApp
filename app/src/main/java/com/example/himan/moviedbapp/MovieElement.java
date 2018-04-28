package com.example.himan.moviedbapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by himan on 24-Mar-18.
 */
@Entity(tableName = "Movie")
public class MovieElement {
    //Serialized name

    @PrimaryKey
    @NonNull
    private String title;
@SerializedName("poster_path")
    private String posterUrl;

@SerializedName("vote_average")
    private double rating;
@Nullable
    private String backdrop_path;
    private String adult;
@SerializedName("overview")
    private String synopsis;
    private int id;

    private boolean isLiked;


    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public MovieElement() {
    }

    public MovieElement(int id) {
        this.id = id;
    }

    public String getAdult() {
        return adult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    private String release_date;

    public MovieElement(String title, String posterUrl, double rating) {
        this.title = title;
        this.posterUrl = posterUrl;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


}
