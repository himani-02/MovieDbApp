package com.example.himan.moviedbapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by himan on 16-Apr-18.
 */

public class TVRecyclerViewAdapter extends RecyclerView.Adapter<TVRecyclerViewAdapter.ViewHolder> {

    interface onItemClickListner{
        void onItemClick(int position);
    }
    ArrayList<TVElement> tv_list;
    Context context;
    boolean bigView;
    TVRecyclerViewAdapter.onItemClickListner mlistner;

    public TVRecyclerViewAdapter(ArrayList<TVElement> tv_list, Context context, TVRecyclerViewAdapter.onItemClickListner mlistner,boolean bigView) {
        this.tv_list = tv_list;
        this.context = context;
        this.mlistner = mlistner;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TVElement movie=tv_list.get(position);
        holder.movie_name.setText(movie.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistner.onItemClick(holder.getAdapterPosition());
            }
        });
        Picasso.get().load("http://image.tmdb.org/t/p/w780/"+movie.getPoster_path()).into(holder.poster);
        holder.poster.setClipToOutline(true);
        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                movie.setLiked(true);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                movie.setLiked(false);

            }
        });
    }

    @Override
    public int getItemCount() {
        return tv_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LikeButton likeButton;
        TextView movie_name;
        ImageView poster;
        View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            likeButton=itemView.findViewById(R.id.like_button);
            movie_name=itemView.findViewById(R.id.movie_name);
            poster=itemView.findViewById(R.id.movie_cover);
        }
    }
}
