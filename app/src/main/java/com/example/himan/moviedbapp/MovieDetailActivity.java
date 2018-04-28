package com.example.himan.moviedbapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends YouTubeBaseActivity {
    ImageView imageView;
    TextView movieName;
    TextView synopsis;
    TextView release_date;
    TextView rating;
    TextView SimilarMovies;
    TextView Trailers;

    TextView textView,textView2;
    TableRow tb1,tb2;

    MovieElement movieElement;
    TVElement tvElement;

    ArrayList<VideoElement>videoElements=new ArrayList<>();
    VideoRecyclerViewAdapter videoRecyclerViewAdapter;
    RecyclerView video_RecyclerView;

    CastRecyclerViewAdapter castRecyclerViewAdapter;
    ArrayList<CastElement>castElements=new ArrayList<>();
    RecyclerView recyclerView;

    MovieRecyclerViewAdapter similarMoviesAdapter;
    ArrayList<MovieElement>similar_movies=new ArrayList<>();
    RecyclerView similar_movies_recyclerView;

    TVRecyclerViewAdapter similarTVAdapter;
    ArrayList<TVElement>similar_Tv=new ArrayList<>();
    RecyclerView similar_tv_recyclerView;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        //getSupportActionBar().hide();
        textView=findViewById(R.id.textView_cast);
        tb1=findViewById(R.id.hr);
        tb2=findViewById(R.id.hr2);
        textView2=findViewById(R.id.videos);

        imageView=findViewById(R.id.imageView_backdrop);
        movieName=findViewById(R.id.name);
        synopsis=findViewById(R.id.synopsis);
        release_date=findViewById(R.id.release_date);
        rating=findViewById(R.id.rating_movie);
        SimilarMovies=findViewById(R.id.similar_movies);
        Trailers=findViewById(R.id.videos);
        intent=getIntent();
        int id=intent.getIntExtra(Constants.ID,-1);
        String name=intent.getStringExtra(Constants.NAME);
        String isMovie=intent.getStringExtra(Constants.IS_MOVIE);

        if(isMovie.equals("true")){
            movieElement=new MovieElement(id);
            movieElement.setTitle(name);
            fetchMovie();

            recyclerView=findViewById(R.id.recyclerView_cast);
            video_RecyclerView=findViewById(R.id.recyclerView_videos);
            similar_movies_recyclerView=findViewById(R.id.recyclerView_similar_movies);
            if(movieElement.getId()!=-1){
                fetchCastList(movieElement.getId());
                fetchVideos(movieElement.getId());
                fetchSimilarMovie();
        }
        }

        else{
            tvElement=new TVElement(id);
            tvElement.setName(name);
            fetchTV();
            recyclerView=findViewById(R.id.recyclerView_cast);
            video_RecyclerView=findViewById(R.id.recyclerView_videos);
            similar_tv_recyclerView=findViewById(R.id.recyclerView_similar_movies);
            if(tvElement.getId()!=-1) {
                fetchTVCastList(tvElement.getId());
                fetchTVVideos(tvElement.getId());
                fetchSimilarTV();
            }

        }

    }

    private void fetchTVVideos(int id) {
        Trailers.setVisibility(View.GONE);
        Call<Video>call=ApiClient.getInstance().getContentApi().getTVVideos(id);
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                Video videos=response.body();
                if(videos!=null){
                    Trailers.setText("Trailers");
                    videoElements.clear();
                    videoElements.addAll(videos.getResults());

                }
                if(!videoElements.isEmpty()){
                    Trailers.setVisibility(View.VISIBLE);
                }
                videoRecyclerViewAdapter=new VideoRecyclerViewAdapter(videoElements, MovieDetailActivity.this, new VideoRecyclerViewAdapter.onItemClickListner() {
                    @Override
                    public void onItemClick(final int pos,YouTubePlayerView v) {

                        YouTubePlayer.OnInitializedListener mlistner=new YouTubePlayer.OnInitializedListener() {
                            @Override
                            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoElements.get(pos).getKey()));
                                startActivity(intent);
                            }

                            @Override
                            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                                Toast.makeText(MovieDetailActivity.this, "Error loading video", Toast.LENGTH_SHORT).show();
                            }
                        };
                        v.initialize(Video.getApiKey(),mlistner);

                    }
                });
                video_RecyclerView.setAdapter(videoRecyclerViewAdapter);
                video_RecyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
                video_RecyclerView.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchTVCastList(int id) {
        tb1.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        Call<CastObject>call=ApiClient.getInstance().getContentApi().getTVCastList(id);
        call.enqueue(new Callback<CastObject>() {
            @Override
            public void onResponse(Call<CastObject> call, Response<CastObject> response) {
                CastObject object=response.body();
//                Log.d("TAGGER",response.body().toString());
//                Log.d("TAGGER",response.code()+"");

                if(object!=null){

                    castElements.clear();
                    for(int i=0;i<10&&i<object.getCast().size();i++){
                        if(object.getCast().get(i).getProfile_path()!=null){
                            castElements.add(object.getCast().get(i));
                            //castRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    }
                    if(!castElements.isEmpty()){
                        tb1.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                    }
                    castRecyclerViewAdapter=new CastRecyclerViewAdapter(new CastRecyclerViewAdapter.OnItemClickListner() {
                        @Override
                        public void onItemClick(int position) {
                            CastElement actor=castElements.get(position);
                            Intent intent=new Intent(MovieDetailActivity.this,ActorDetailActivity2.class);
                            intent.putExtra(Constants.ID,actor.getId());
                            startActivity(intent);
                        }
                    },MovieDetailActivity.this,castElements);
                    recyclerView.setAdapter(castRecyclerViewAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                }
            }

            @Override
            public void onFailure(Call<CastObject> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchSimilarTV() {
        SimilarMovies.setVisibility(View.GONE);
        tb2.setVisibility(View.GONE);
        Call<TVObject>call=ApiClient.getInstance().getContentApi().getsimilarTV(tvElement.getId()+"");
        call.enqueue(new Callback<TVObject>() {
            @Override
            public void onResponse(Call<TVObject> call, Response<TVObject> response) {
                TVObject tvObject=response.body();
                if(tvObject.getResults()!=null){
                    SimilarMovies.setText("Similar TV Shows");
                    similar_Tv.clear();
                    similar_Tv.addAll(tvObject.getResults());
                }
                if(!similar_Tv.isEmpty()){
                    SimilarMovies.setVisibility(View.VISIBLE);
                    tb2.setVisibility(View.VISIBLE);
                }
                similarTVAdapter=new TVRecyclerViewAdapter(similar_Tv, MovieDetailActivity.this, new TVRecyclerViewAdapter.onItemClickListner() {
                    @Override
                    public void onItemClick(int position) {
                        TVElement tvElement=similar_Tv.get(position);
                        Intent intent=new Intent(MovieDetailActivity.this,MovieDetailActivity.class);
                        intent.putExtra(Constants.ID,tvElement.getId());
                        intent.putExtra(Constants.NAME,tvElement.getName());
                        intent.putExtra(Constants.IS_MOVIE,"false");
                        startActivity(intent);
                    }
                },false);
                similar_tv_recyclerView.setAdapter(similarTVAdapter);
                similar_tv_recyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL,false));
                similar_tv_recyclerView.setItemAnimator(new DefaultItemAnimator());
                return;
            }

            @Override
            public void onFailure(Call<TVObject> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchTV() {
        Call<TVElement>call=ApiClient.getInstance().getContentApi().getTVDetail(tvElement.getId());
        call.enqueue(new Callback<TVElement>() {
            @Override
            public void onResponse(Call<TVElement> call, Response<TVElement> response) {
                if(response.body()!=null){
                    TVElement temp=response.body();
                    tvElement.setName(temp.getName());
                    tvElement.setBackdrop_path(temp.getBackdrop_path());
                    tvElement.setOverview(temp.getOverview());
                    tvElement.setFirst_air_date(temp.getFirst_air_date());
                    tvElement.setNumber_of_episodes(temp.getNumber_of_episodes());
                    Picasso.get().load( "http://image.tmdb.org/t/p/w780/"+tvElement.getBackdrop_path()).into(imageView);
                    movieName.setText(tvElement.getName());
                    synopsis.setText(tvElement.getOverview());
                    release_date.setText(tvElement.getFirst_air_date());
                    rating.setText("Number of episodes : "+tvElement.getNumber_of_episodes());
                    return;
                }
            }

            @Override
            public void onFailure(Call<TVElement> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchSimilarMovie() {
        SimilarMovies.setVisibility(View.GONE);
        tb2.setVisibility(View.GONE);
        Call<MovieObject>call=ApiClient.getInstance().getContentApi().getSimilarMovies(movieElement.getId()+"");
        call.enqueue(new Callback<MovieObject>() {
            @Override
            public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
                MovieObject movieObject=response.body();
                if(movieObject.getResults()!=null){
                    SimilarMovies.setText("Similar Movies");
                    similar_movies.clear();
                    similar_movies.addAll(movieObject.getResults());
                }
                if(!similar_movies.isEmpty()){
                    SimilarMovies.setVisibility(View.VISIBLE);
                    tb2.setVisibility(View.VISIBLE);
                }
                similarMoviesAdapter=new MovieRecyclerViewAdapter(similar_movies, MovieDetailActivity.this, new MovieRecyclerViewAdapter.onItemClickListner() {
                    @Override
                    public void onItemClick(int position) {
                        MovieElement movieElement=similar_movies.get(position);
                        Intent intent=new Intent(MovieDetailActivity.this,MovieDetailActivity.class);
                        intent.putExtra(Constants.ID,movieElement.getId());
                        intent.putExtra(Constants.NAME,movieElement.getTitle());
                        intent.putExtra(Constants.IS_MOVIE,"true");
                        startActivity(intent);
                    }
                },false);
                similar_movies_recyclerView.setAdapter(similarMoviesAdapter);
                similar_movies_recyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL,false));
                similar_movies_recyclerView.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void onFailure(Call<MovieObject> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchVideos(int id) {
        Trailers.setVisibility(View.GONE);
        Call<Video>call=ApiClient.getInstance().getContentApi().getVideos(id);
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                Video videos=response.body();
                if(videos!=null){
                    Trailers.setText("Trailers");
                    videoElements.clear();
                    videoElements.addAll(videos.getResults());

                }
                if(!videoElements.isEmpty()){
                    Trailers.setVisibility(View.VISIBLE);
                }
                videoRecyclerViewAdapter=new VideoRecyclerViewAdapter(videoElements, MovieDetailActivity.this, new VideoRecyclerViewAdapter.onItemClickListner() {
                    @Override
                    public void onItemClick(final int pos,YouTubePlayerView v) {

                        YouTubePlayer.OnInitializedListener mlistner=new YouTubePlayer.OnInitializedListener() {
                            @Override
                            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoElements.get(pos).getKey()));
                                startActivity(intent);
                            }

                            @Override
                            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                                Toast.makeText(MovieDetailActivity.this, "Error loading video", Toast.LENGTH_SHORT).show();
                            }
                        };
                        v.initialize(Video.getApiKey(),mlistner);

                    }
                });
                video_RecyclerView.setAdapter(videoRecyclerViewAdapter);
                video_RecyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
                video_RecyclerView.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCastList(int id) {
        tb1.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        Call<CastObject>call=ApiClient.getInstance().getContentApi().getCastList(id);
        call.enqueue(new Callback<CastObject>() {
            @Override
            public void onResponse(Call<CastObject> call, Response<CastObject> response) {
                CastObject object=response.body();
//                Log.d("TAGGER",response.body().toString());
//                Log.d("TAGGER",response.code()+"");

                if(object!=null){

                    castElements.clear();
                    for(int i=0;i<10&&i<object.getCast().size();i++){
                        if(object.getCast().get(i).getProfile_path()!=null){
                            castElements.add(object.getCast().get(i));
                            //castRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    }
                    if(!castElements.isEmpty()){
                        tb1.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                    }
                    castRecyclerViewAdapter=new CastRecyclerViewAdapter(new CastRecyclerViewAdapter.OnItemClickListner() {
                        @Override
                        public void onItemClick(int position) {
                            CastElement actor=castElements.get(position);
                            Intent intent=new Intent(MovieDetailActivity.this,ActorDetailActivity2.class);
                            intent.putExtra(Constants.ID,actor.getId());
                            startActivity(intent);
                        }
                    },MovieDetailActivity.this,castElements);
                    recyclerView.setAdapter(castRecyclerViewAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                }
            }

            @Override
            public void onFailure(Call<CastObject> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMovie() {
        //final ArrayList<MovieElement>movieElements=new ArrayList<>();


        Call<MovieObject> call=ApiClient.getInstance().getContentApi().getMovieDetail("f0b5a552d8b1888afc7ecc4545858d4e",movieElement.getTitle());
        call.enqueue(new Callback<MovieObject>() {
            @Override
            public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
                MovieObject movieObject=response.body();
                if(movieObject!=null){
                    for(int i=0;i<movieObject.getResults().size();i++){
                        if(movieObject.getResults().get(i).getId()==movieElement.getId()){
                            movieElement.setTitle(movieObject.getResults().get(i).getTitle());
                            movieElement.setBackdrop_path(movieObject.getResults().get(i).getBackdrop_path());
                            movieElement.setSynopsis(movieObject.getResults().get(i).getSynopsis());
                            movieElement.setRelease_date(movieObject.getResults().get(i).getRelease_date());
                            movieElement.setRating(movieObject.getResults().get(i).getRating());
                            Picasso.get().load( "http://image.tmdb.org/t/p/w780/"+movieElement.getBackdrop_path()).into(imageView);
                            movieName.setText(movieElement.getTitle());
                            synopsis.setText(movieElement.getSynopsis());
                            rating.setText(movieElement.getRating()+"★");  //issue
                            release_date.setText(movieElement.getRelease_date());
                            return;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieObject> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return;
    }
}
