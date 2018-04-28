package com.example.himan.moviedbapp;

import java.util.ArrayList;

/**
 * Created by himan on 22-Apr-18.
 */

public class ActorTV {
    private int id;
    private ArrayList<TVElement>cast;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<TVElement> getCast() {
        return cast;
    }

    public void setCast(ArrayList<TVElement> cast) {
        this.cast = cast;
    }
}
