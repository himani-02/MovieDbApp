package com.example.himan.moviedbapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by himan on 24-Mar-18.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    interface onItemClickListner{
        void onItemClick(int position);
    }

    ArrayList<MovieElement> movies;
    Context context;
    onItemClickListner mlistner;
    boolean bigView;

    public MovieRecyclerViewAdapter(ArrayList<MovieElement> movies, Context context,onItemClickListner listner,boolean bigView) {
        this.movies = movies;
        this.context = context;
        this.mlistner=listner;
        this.bigView=bigView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView;
        ViewHolder holder;
        if(bigView==false){
            itemView=inflater.inflate(R.layout.element_layout,parent,false);
            holder=new ViewHolder(itemView);
        }
        else{
            itemView=inflater.inflate(R.layout.element_layout,parent,false);
            int dimensionInDpParent = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, context.getResources().getDisplayMetrics());
            itemView.setLayoutParams(new LinearLayout.LayoutParams(dimensionInDpParent,ViewGroup.LayoutParams.MATCH_PARENT));
            holder=new ViewHolder(itemView);

            int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 440, context.getResources().getDisplayMetrics());
            holder.poster.requestLayout();
            holder.poster.getLayoutParams().height=dimensionInDp;
            holder.poster.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.poster.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }


        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MovieElement movie=movies.get(position);
        holder.movie_name.setText(movie.getTitle());
        holder.rating.setText(movie.getRating()+"★");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistner.onItemClick(holder.getAdapterPosition());
            }
        });
        Picasso.get().load("http://image.tmdb.org/t/p/w780/"+movie.getPosterUrl()).into(holder.poster);
        holder.poster.setClipToOutline(true);
        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                movie.setLiked(false);

            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LikeButton likeButton;
        TextView movie_name;
        ImageView poster;
        TextView rating;
        View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            likeButton=itemView.findViewById(R.id.like_button);
            movie_name=itemView.findViewById(R.id.movie_name);
            poster=itemView.findViewById(R.id.movie_cover);
            rating=itemView.findViewById(R.id.movie_rating);
        }
    }
}
