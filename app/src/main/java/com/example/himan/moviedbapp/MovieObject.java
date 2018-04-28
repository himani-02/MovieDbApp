package com.example.himan.moviedbapp;

import java.util.ArrayList;

/**
 * Created by himan on 25-Mar-18.
 */

public class MovieObject {
    private ArrayList<MovieElement>results;
    private int page;
    private int total_results;
    private int total_pages;

    public ArrayList<MovieElement> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieElement> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }


}
