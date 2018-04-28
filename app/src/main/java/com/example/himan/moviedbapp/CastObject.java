package com.example.himan.moviedbapp;

import java.util.ArrayList;

/**
 * Created by himan on 07-Apr-18.
 */

public class CastObject {
    private int id;
    private ArrayList<CastElement>cast;
    private ArrayList<CastElement>crew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<CastElement> getCast() {
        return cast;
    }

    public void setCast(ArrayList<CastElement> cast) {
        this.cast = cast;
    }

    public ArrayList<CastElement> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<CastElement> crew) {
        this.crew = crew;
    }
}
