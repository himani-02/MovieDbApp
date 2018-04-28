package com.example.himan.moviedbapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by himan on 24-Mar-18.
 */

public interface ContentApi {

    @GET("/3/movie/popular?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<MovieObject> getPopularMovie();

    @GET("/3/search/movie")
    Call<MovieObject> getMovieDetail(@Query("api_key")String key,@Query("query") String arg);

    @GET("/3/movie/{id}/casts?api_key=f0b5a552d8b1888afc7ecc4545858d4e")
    Call<CastObject> getCastList(@Path("id")int id);

    @GET("/3/tv/{tv_id}/credits?api_key=f0b5a552d8b1888afc7ecc4545858d4e")
    Call<CastObject> getTVCastList(@Path("tv_id")int id);

    @GET("/3/movie/now_playing?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<MovieObject> getNowShowingMovie();

    @GET("3/movie/top_rated?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<MovieObject> getTopRatedMovie();

    @GET("3/movie/upcoming?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<MovieObject> getUpcomingMovie();

    @GET("/3/tv/popular?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<TVObject> getTVPopular();

    @GET("/3/tv/top_rated?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<TVObject> getTVTopRated();

    @GET("/3/tv/airing_today?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<TVObject> getTVAiringToday();

    @GET("/3/tv/on_the_air?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<TVObject> getTVOnTheAir();

    @GET("/3/movie/{id}/videos?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<Video>getVideos(@Path("id")int id);

    @GET("/3/tv/{tv_id}/videos?api_key=f0b5a552d8b1888afc7ecc4545858d4e")
    Call<Video>getTVVideos(@Path("tv_id")int id);

    @GET("/3/movie/{movie_id}/similar?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<MovieObject>getSimilarMovies(@Path("movie_id")String id);

    @GET("/3/person/{person_id}/movie_credits?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<ActorMovies>getMoviesByActor(@Path("person_id")String id);

    @GET("/3/person/{person_id}/tv_credits?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<ActorTV>getTVByActor(@Path("person_id")String id);

    @GET("/3/person/{person_id}?api_key=f0b5a552d8b1888afc7ecc4545858d4e")
    Call<Actor>getActor(@Path("person_id")String id);

    @GET("/3/tv/{tv_id}?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<TVElement> getTVDetail(@Path("tv_id")int id);

    @GET("/3/tv/{tv_id}/similar?api_key=f0b5a552d8b1888afc7ecc4545858d4e&language=en-US")
    Call<TVObject>getsimilarTV(@Path("tv_id")String id);
}
