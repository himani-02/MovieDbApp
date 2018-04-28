package com.example.himan.moviedbapp;

import java.util.ArrayList;

/**
 * Created by himan on 21-Apr-18.
 */

public class Video {
    private static final String API_KEY="AIzaSyDKw21DMyN5j7J58aLiytuSrtSOED-rBD4";

    public static String getApiKey() {
        return API_KEY;
    }

    private int id;
    private ArrayList<VideoElement>results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<VideoElement> getResults() {
        return results;
    }

    public void setResults(ArrayList<VideoElement> results) {
        this.results = results;
    }
}
