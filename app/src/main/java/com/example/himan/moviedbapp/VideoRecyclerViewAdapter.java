package com.example.himan.moviedbapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

/**
 * Created by himan on 21-Apr-18.
 */

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder> {
    ArrayList<VideoElement>videos;
    Context context;
    interface onItemClickListner{
        void onItemClick(int pos,YouTubePlayerView v);
    }
    onItemClickListner mlistner;

    public VideoRecyclerViewAdapter(ArrayList<VideoElement> videos, Context context,onItemClickListner mlistner) {
        this.videos = videos;
        this.context = context;
        this.mlistner=mlistner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=layoutInflater.inflate(R.layout.video_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        VideoElement videoElement=videos.get(position);
        holder.textView.setText(videoElement.getName());
        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistner.onItemClick(holder.getAdapterPosition(),holder.videoView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        YouTubePlayerView videoView;
        Button playButton;
        TextView textView;
        View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            playButton=itemView.findViewById(R.id.play_button);
            videoView=(YouTubePlayerView)itemView.findViewById(R.id.youTubePlayerView);
            textView=itemView.findViewById(R.id.video_name);
        }
    }
}
