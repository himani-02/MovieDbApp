package com.example.himan.moviedbapp;

import java.util.ArrayList;

/**
 * Created by himan on 22-Apr-18.
 */

public class ActorMovies {
    private int id;
    private ArrayList<MovieElement>cast;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<MovieElement> getCast() {
        return cast;
    }

    public void setCast(ArrayList<MovieElement> cast) {
        this.cast = cast;
    }
}
