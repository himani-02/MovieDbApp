package com.example.himan.moviedbapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActorDetailActivity2 extends AppCompatActivity {
    TextView name;
    TextView biography;
    TextView birthday;
    TextView deathday;
    ImageView profile_pic;
    TextView movies_by_actor;
    TextView tv_by_actor;

    CollapsingToolbarLayout collapsingToolbarLayout;
    Actor actor;
    RecyclerView moviesRecyclerView;
    RecyclerView tvRecyclerView;
    MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    TVRecyclerViewAdapter tvRecyclerViewAdapter;
    ArrayList<MovieElement> movie_list=new ArrayList<>();
    ArrayList<TVElement>tv_list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout=findViewById(R.id.toolbar_layout);
        name=findViewById(R.id.actor_name);
        biography=findViewById(R.id.biography);
        birthday=findViewById(R.id.date_of_birth);
        deathday=findViewById(R.id.date_of_death);
        profile_pic=findViewById(R.id.profile_pic);
        movies_by_actor=findViewById(R.id.movies_by_actor);
        tv_by_actor=findViewById(R.id.tv_by_actor);

        moviesRecyclerView=findViewById(R.id.recyclerView_actor_movies);
        tvRecyclerView=findViewById(R.id.recyclerView_actor_tv);

        Intent intent=getIntent();
        int id=intent.getIntExtra(Constants.ID,-1);
        if(id!=-1){
            getActor(id);
            getMoviesByActor(id);
            getTVByActor(id);
        }

    }

    private void getTVByActor(int id) {
        Call<ActorTV> call=ApiClient.getInstance().getContentApi().getTVByActor(id+"");
        call.enqueue(new Callback<ActorTV>() {
            @Override
            public void onResponse(Call<ActorTV> call, Response<ActorTV> response) {
                ActorTV actorTV=response.body();
                if(actorTV.getCast()!=null){
                    tv_by_actor.setText("TV Shows by "+actor.getName());
                    tv_list.clear();
                    tv_list.addAll(actorTV.getCast());
                }
                tvRecyclerViewAdapter=new TVRecyclerViewAdapter(tv_list, ActorDetailActivity2.this, new TVRecyclerViewAdapter.onItemClickListner() {
                    @Override
                    public void onItemClick(int position) {
                        //TODO
                    }
                },false);
                tvRecyclerView.setAdapter(tvRecyclerViewAdapter);
                tvRecyclerView.setLayoutManager(new LinearLayoutManager(ActorDetailActivity2.this,LinearLayoutManager.HORIZONTAL,false));
                tvRecyclerView.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void onFailure(Call<ActorTV> call, Throwable t) {
                Toast.makeText(ActorDetailActivity2.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMoviesByActor(int id) {
        Call<ActorMovies>call=ApiClient.getInstance().getContentApi().getMoviesByActor(id+"");
        call.enqueue(new Callback<ActorMovies>() {
            @Override
            public void onResponse(Call<ActorMovies> call, Response<ActorMovies> response) {
                ActorMovies actorMovies=response.body();
                if(actorMovies.getCast()!=null){
                    movies_by_actor.setText("Movies by "+actor.getName());
                    movie_list.clear();
                    movie_list.addAll(actorMovies.getCast());
                }
                movieRecyclerViewAdapter=new MovieRecyclerViewAdapter(movie_list, ActorDetailActivity2.this, new MovieRecyclerViewAdapter.onItemClickListner() {
                    @Override
                    public void onItemClick(int position) {
                        MovieElement movieElement=movie_list.get(position);
                        Intent intent=new Intent(ActorDetailActivity2.this,MovieDetailActivity.class);
                        intent.putExtra(Constants.ID,movieElement.getId());
                        intent.putExtra(Constants.NAME,movieElement.getTitle());
                        startActivity(intent);
                    }
                },false);
                moviesRecyclerView.setAdapter(movieRecyclerViewAdapter);
                moviesRecyclerView.setLayoutManager(new LinearLayoutManager(ActorDetailActivity2.this,LinearLayoutManager.HORIZONTAL,false));
                moviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void onFailure(Call<ActorMovies> call, Throwable t) {
                Toast.makeText(ActorDetailActivity2.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getActor(int id) {
        Call<Actor>call=ApiClient.getInstance().getContentApi().getActor(id+"");
        call.enqueue(new Callback<Actor>() {
            @Override
            public void onResponse(Call<Actor> call, Response<Actor> response) {
                if(response.body()!=null){
                    actor=response.body();
                    name.setText(actor.getName());
                    collapsingToolbarLayout.setTitle(actor.getName());
                    if(actor.getBirthday()!=null){
                        birthday.setText(actor.getBirthday());
                    }
                    if(actor.getDeathday()!=null){
                        birthday.setText("to "+actor.getDeathday());
                    }
                    biography.setText(actor.getBiography());
                    if(actor.getProfile_path()!=null){
                        Picasso.get().load("http://image.tmdb.org/t/p/w342/"+actor.getProfile_path()).into(profile_pic);
                    }

                }
            }

            @Override
            public void onFailure(Call<Actor> call, Throwable t) {
                Toast.makeText(ActorDetailActivity2.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
