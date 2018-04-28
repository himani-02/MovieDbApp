package com.example.himan.moviedbapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by himan on 24-Mar-18.
 */

public class ApiClient {
    private static ApiClient INSTANCE;
    private ContentApi contentApi;

    private ApiClient() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        contentApi = retrofit.create(ContentApi.class);
    }
    public static ApiClient getInstance(){
        if(INSTANCE==null){
            INSTANCE=new ApiClient();
        }
        return INSTANCE;
    }
    public ContentApi getContentApi(){
        return contentApi;
    }
}
